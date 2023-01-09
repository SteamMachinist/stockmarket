package steammachinist.stockmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.*;
import steammachinist.stockmarket.service.dataservice.*;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TradeManagerService implements ApplicationListener<NewOfferEvent> {
    private final UserService userService;
    private final PositionService positionService;
    private final StockService stockService;
    private final OfferService offerService;
    private final TradeService tradeService;

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void onApplicationEvent(NewOfferEvent event) {
        Offer offer = event.getOffer();
        Offer counteroffer = findCounteroffer(offer);
        if (counteroffer != null) {
            doTrade(offer, counteroffer);
        }
    }

    private Offer findCounteroffer(Offer offer) {
        Offer counteroffer;
        if (offer.getType() == OfferType.BUY) {
            counteroffer = offerService.findByOppositeTypeAndSmallerThanEqualPrice(offer);
        } else {
            counteroffer = offerService.findByOppositeTypeAndBiggerThanEqualPrice(offer);
        }
        return counteroffer;
    }

    private void doTrade(Offer offer1, Offer offer2) {
        Offer buy;
        Offer sell;
        if (offer1.getType() == OfferType.BUY) {
            buy = offer1;
            sell = offer2;
        } else {
            buy = offer2;
            sell = offer1;
        }

        double unitPrice = sell.getUnitPrice();
        int quantity = Math.min(buy.getQuantity(), sell.getQuantity());
        double sum = unitPrice * quantity;

        if (!isTradePossible(buy, sell, sum, quantity)) {
            return;
        }
        try {
            updateUsersPositions(buy.getCreator(), sell.getCreator(), sell.getStock(), quantity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateUsersBalances(buy.getCreator(), sell.getCreator(), sum);
        updateUsersOffers(buy, sell, quantity);
        Trade trade = new Trade(sell.getCreator(), buy.getCreator(),
                sell.getStock(), unitPrice, quantity, LocalDateTime.now());
        tradeService.addTrade(trade);
    }

    private boolean isTradePossible(Offer buy, Offer sell, double sum, int quantity) {
        return buy.getCreator().getBalance() >= sum && sell.getQuantity() >= quantity;
    }

    private void updateUsersPositions(User buyer, User seller, Stock stock, int quantity) throws Exception {
        PositionId buyerPositionId = new PositionId(stock, buyer);
        try {
            positionService.addPosition(new Position(buyerPositionId,
                    positionService.findById(buyerPositionId).getQuantity() + quantity));
        } catch (Exception e) {
            positionService.addPosition(new Position(buyerPositionId, quantity));;
        }

        PositionId sellerPositionId = new PositionId(stock, seller);
        if (positionService.findById(sellerPositionId).getQuantity() == quantity) {
            positionService.deletePosition(new Position(sellerPositionId, quantity));
        }
        else {
            positionService.addPosition(new Position(sellerPositionId,
                    positionService.findById(sellerPositionId).getQuantity() - quantity));
        }
    }

    private void updateUsersBalances(User buyer, User seller, double sum) {
        buyer.setBalance(buyer.getBalance() - sum);
        seller.setBalance(seller.getBalance() + sum);
        userService.updateUser(buyer);
        userService.updateUser(seller);
        offerService.findAllUserOffers(buyer).forEach(offer ->
                applicationEventPublisher.publishEvent(new NewOfferEvent(this, offer)));
        offerService.findAllUserOffers(seller).forEach(offer ->
                applicationEventPublisher.publishEvent(new NewOfferEvent(this, offer)));
    }

    private void updateUsersOffers(Offer buy, Offer sell, int quantity) {
        if (buy.getQuantity() == quantity) {
            offerService.deleteOffer(buy);
        } else {
            buy.setQuantity(buy.getQuantity() - quantity);
            offerService.addOffer(buy);
        }
        if (sell.getQuantity() == quantity) {
            offerService.deleteOffer(sell);
        } else {
            sell.setQuantity(sell.getQuantity() - quantity);
            offerService.addOffer(sell);
        }
    }
}

package steammachinist.stockmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.Offer;
import steammachinist.stockmarket.entitymodel.Position;
import steammachinist.stockmarket.entitymodel.PositionId;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.service.dataservice.OfferService;
import steammachinist.stockmarket.service.dataservice.PositionService;
import steammachinist.stockmarket.service.dataservice.StockService;
import steammachinist.stockmarket.service.dataservice.UserService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OfferManagerService {
    private final UserService userService;
    private final PositionService positionService;
    private final StockService stockService;
    private final OfferService offerService;

    public boolean checkEnoughBalance(User user, Offer offer) {
        return user.getBalance() >= offer.getUnitPrice() * offer.getQuantity();
    }

    public boolean checkEnoughPositionQuantity(User user, Offer offer) {
        try {
            Position position = positionService.findById(new PositionId(offer.getStock(), user));
            return position.getQuantity() >= offer.getQuantity();
        } catch (Exception e) {
            return false;
        }
    }

    public void addOfferFromUserAtDatetime(Offer offer, User user, LocalDateTime openDateTime) {
        offer.setCreator(user);
        offer.setOpen(openDateTime);
        offerService.addOffer(offer);
    }
}

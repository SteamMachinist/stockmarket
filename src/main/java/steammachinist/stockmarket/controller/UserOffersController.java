package steammachinist.stockmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import steammachinist.stockmarket.entitymodel.Offer;
import steammachinist.stockmarket.entitymodel.OfferType;
import steammachinist.stockmarket.entitymodel.Stock;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.service.*;
import steammachinist.stockmarket.service.dataservice.OfferService;
import steammachinist.stockmarket.service.dataservice.PositionService;
import steammachinist.stockmarket.service.dataservice.StockService;
import steammachinist.stockmarket.service.dataservice.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account/offer")
@RequiredArgsConstructor
public class UserOffersController {
    private final UserService userService;
    private final PositionService positionService;
    private final StockService stockService;
    private final OfferService offerService;

    private final OfferManagerService offerManagerService;

    @GetMapping("/create/{type}")
    public String setupCreateOffer(@AuthenticationPrincipal User user, Model model, @PathVariable String type) {
        OfferType offerType = OfferType.valueOf(type.toUpperCase());

        List<Stock> stocksForOffer = new ArrayList<>();
        if (offerType == OfferType.BUY) {
            stocksForOffer.addAll(stockService.getAllStocks());
        }
        if (offerType == OfferType.SELL) {
            stocksForOffer.addAll(positionService.getUserStocks(user));
        }

        model.addAttribute("type", offerType.name());
        model.addAttribute("offerForm", new Offer());
        model.addAttribute("chosenStock", new Stock());
        model.addAttribute("stocksList", stocksForOffer);
        return "create_offer";
    }

    @PostMapping("/create/{type}")
    public String createOffer(@AuthenticationPrincipal User user, Model model, Offer offer, @PathVariable String type) {
        String resultMessage = "";
        if (offer.getType() == OfferType.BUY && !offerManagerService.checkEnoughBalance(user, offer)) {
            resultMessage = "Can't create buy offer: not enough balance";
        }
        else if (offer.getType() == OfferType.SELL && !offerManagerService.checkEnoughPositionQuantity(user, offer)){
            resultMessage = "Can't create sell offer: not enough stocks";
        }
        else {
            offerManagerService.addOfferFromUserAtDatetime(offer, user, LocalDateTime.now());
            resultMessage = "Offer created successfully";
        }
        model.addAttribute("resultMessage", resultMessage);
        return setupCreateOffer(user, model, type);
    }
}

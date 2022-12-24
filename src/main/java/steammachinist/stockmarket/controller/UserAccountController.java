package steammachinist.stockmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import steammachinist.stockmarket.entitymodel.*;
import steammachinist.stockmarket.repository.OfferRepository;
import steammachinist.stockmarket.service.PositionService;
import steammachinist.stockmarket.service.StockService;
import steammachinist.stockmarket.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserService userService;
    private final PositionService positionService;
    private final StockService stockService;
    private final OfferRepository offerRepository;


    @GetMapping()
    public String getAccountIndex(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user_account";
    }

    @GetMapping("/positions")
    public String getUserPositions(@AuthenticationPrincipal User user, Model model) {
        List<Position> positions = positionService.getUserPositions(user);
        model.addAttribute("positions", positions);
        return "user_positions";
    }

    @GetMapping("/create_offer")
    public String setupCreateOffer(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("offerForm", new Offer());

        model.addAttribute("chosenType", OfferType.BUY);
        model.addAttribute("types", List.of(OfferType.BUY, OfferType.SELL));

        model.addAttribute("chosenStock", new Stock());
        model.addAttribute("allStocksList", stockService.getAllStocks());
        model.addAttribute("userStocksList", positionService.getUserPositions(user)
                .stream()
                .map(position -> position.getPositionId().getStock()));
        return "create_offer";
    }

    @PostMapping("/create_offer")
    public String createOffer(@AuthenticationPrincipal User user, Model model, Offer offer) {
        offer.setCreator(user);
        offer.setOpen(LocalDateTime.now());
        offerRepository.save(offer);
        return "redirect:/account/create_offer";
    }
}

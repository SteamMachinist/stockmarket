package steammachinist.stockmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import steammachinist.stockmarket.entitymodel.*;
import steammachinist.stockmarket.service.dataservice.OfferService;
import steammachinist.stockmarket.service.dataservice.PositionService;
import steammachinist.stockmarket.service.dataservice.StockService;
import steammachinist.stockmarket.service.dataservice.UserService;

import java.util.List;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserService userService;
    private final PositionService positionService;
    private final StockService stockService;
    private final OfferService offerService;


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
}

package steammachinist.stockmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import steammachinist.stockmarket.entitymodel.*;
import steammachinist.stockmarket.repository.TradeRepository;
import steammachinist.stockmarket.service.dataservice.OfferService;
import steammachinist.stockmarket.service.dataservice.PositionService;
import steammachinist.stockmarket.service.dataservice.StockService;
import steammachinist.stockmarket.service.dataservice.UserService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserService userService;
    private final PositionService positionService;
    private final StockService stockService;
    private final OfferService offerService;
    private final TradeRepository tradeRepository;


    @GetMapping()
    public String getAccountIndex(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user_account";
    }

    @GetMapping("/positions")
    public String getUserPositions(@AuthenticationPrincipal User user, Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        //List<Position> positions = positionService.getUserPositions(user);
        Page<Position> positionsPage = positionService.getPaginatedUserPositions(user, PageRequest.of(currentPage - 1, pageSize));

        int totalPages = positionsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("positionsPage", positionsPage);
        return "user_positions";
    }

    @GetMapping("/offers")
    public String getUserOffers(@AuthenticationPrincipal User user, Model model) {
        List<Offer> offers = offerService.findAllUserOffers(user);
        model.addAttribute("offers", offers);
        return "user_offers";
    }

    @GetMapping("/trades")
    public String getUserTrades(@AuthenticationPrincipal User user, Model model) {
        List<Trade> trades = tradeRepository.findByBuyerOrSeller(user, user);
        List<OfferType> types = trades.stream().map(trade -> {
            if (trade.getBuyer().equals(user)) {
                return OfferType.BUY;
            } else {
                return OfferType.SELL;
            }
        }).toList();
        model.addAttribute("trades", trades);
        model.addAttribute("types", types);
        return "user_trades";
    }
}

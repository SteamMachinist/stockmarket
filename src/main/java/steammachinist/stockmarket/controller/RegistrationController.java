package steammachinist.stockmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.service.dataservice.UserService;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("userForm") User userForm,
                           @RequestParam(name = "passwordConfirm") String passwordConfirm,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors().toString());
            return "registration";
        }
        if (userService.isUsernameUsed(userForm.getUsername())) {
            model.addAttribute("errorMessage", "Username is already in use");
            return "registration";
        }
        if (!userForm.getPassword().equals(passwordConfirm)) {
            model.addAttribute("errorMessage", "Passwords does not match");
            return "registration";
        }
        userService.addDefaultUser(userForm);
        return "redirect:/";
    }
}

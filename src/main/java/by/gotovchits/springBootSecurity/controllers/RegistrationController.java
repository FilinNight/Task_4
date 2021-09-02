package by.gotovchits.springBootSecurity.controllers;

import by.gotovchits.springBootSecurity.models.User;
import by.gotovchits.springBootSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String newUser(@ModelAttribute("user") User user) {
        return "registration";
    }


    @PostMapping("/registration")
    public String create(@ModelAttribute("user") User user, Model model) {

        if (user.getPassword() != null) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/main";
    }
}

package by.gotovchits.springBootSecurity.controllers;

import by.gotovchits.springBootSecurity.helperСlasses.Foo;
import by.gotovchits.springBootSecurity.models.User;
import by.gotovchits.springBootSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping
    public String greeting() {
        return "login";
    }

    @GetMapping("/main")
    public String main(Model model){

         if(!userService.checkStatus()){
             return "redirect:/login";
         }
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.getAuthenticationUser());
        model.addAttribute("foo", new Foo());
        return "main";
    }

    @PostMapping(value = "/main", params = "action=delete")
    public String deleteForm(@ModelAttribute(value="foo") Foo foo) {

        if(!userService.checkStatus()){
            return "redirect:/login";
        }

        List<User> checkedItems = foo.getCheckedItems();
        for(User user : checkedItems) {
            if(user.getUsername().equals(userService.getAuthenticationUser().getUsername()))
                continue;
            userService.deleteById(user.getId());
        }
        return "redirect:/main";
    }

    @PostMapping(value = "/main", params = "action=unblock")
    public String unblockForm(@ModelAttribute(value="foo") Foo foo) {

        if(!userService.checkStatus()){
            return "redirect:/login";
        }

        List<User> checkedItems = foo.getCheckedItems();
        for(User user : checkedItems) {
            userService.changeStatus(true, user);
        }
        return "redirect:/main";
    }

    @PostMapping(value = "/main", params = "action=block")
    public String blockForm(@ModelAttribute(value="foo") Foo foo) {

        if(!userService.checkStatus()){
            return "redirect:/login";
        }

        List<User> checkedItems = foo.getCheckedItems();
        for(User user : checkedItems) {
            userService.changeStatus(false, user);
        }

        return "redirect:/main";
    }



}
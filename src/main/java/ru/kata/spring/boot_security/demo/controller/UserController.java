package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@ComponentScan(basePackages = "demo")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping(value = "/")
    public String startPage(){
        return "startPage";
    }
    @GetMapping(value = "/registration")
    public String registration(ModelMap modelMap){
        modelMap.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping (value = "/registration")
    public String addRegistration(@ModelAttribute("user")@Valid User user){
        userService.saveUser(user);
        User user1 = userService.findUserByEmail(user.getEmail());
        System.out.println(user1);
        return "redirect:/login";
    }
    @GetMapping(value = "/user")
    public String getUser(Principal principal, ModelMap modelMap){
        String email = principal.getName();
        System.out.println(email);
        modelMap.addAttribute("user", userService.findUserByEmail(email));
        return "user";
    }
    @GetMapping(value = "/admin")
    public String getAllUsers(Principal principal, ModelMap modelMap){
        List<User> users = userService.getAllUsers();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("admin", userService.findUserByEmail(principal.getName()));
        return "admin1";
    }

    @DeleteMapping (value = "/delete/{id}")
    public String delete(@PathVariable ("id") Long id){
        userService.deleteUser(id);
        System.out.println("DELETE USER ID:" + id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/edit/{id}")
    public String editUser(@PathVariable("id") Long id, ModelMap modelMap){
        modelMap.addAttribute("user", userService.getUser(id));
        modelMap.addAttribute("roles", userService.getAllRoles());
        return "edit";
    }
    @GetMapping(value = "/admin/edit/{id}")
    public String edit(@ModelAttribute("user") @Valid User user) {
        userService.editUser(user);
        return "redirect:/admin";
    }
}

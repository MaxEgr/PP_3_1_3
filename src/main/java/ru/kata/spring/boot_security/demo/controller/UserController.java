package ru.kata.spring.boot_security.demo.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@Controller
@ComponentScan(basePackages = "demo")
public class UserController {
    private UserService userService;
}

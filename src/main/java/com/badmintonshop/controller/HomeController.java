package com.badmintonshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home Controller - Handle home and public pages
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping({ "/", "/home" })
    public String home(Model model) {
        return "home";
    }
}

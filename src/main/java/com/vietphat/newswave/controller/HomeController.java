package com.vietphat.newswave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToHomepage() {
        return "redirect:trang-chu";
    }

    @GetMapping("/trang-chu")
    public String homepage() {
        return "views/web/index.html";
    }

    @GetMapping("/quan-tri")
    public String dashboard() {
        return "views/dashboard/index.html";
    }

}

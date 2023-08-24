package com.vietphat.newswave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String redirectToHomepage() {
        return "redirect:trang-chu";
    }

    @RequestMapping("/trang-chu")
    public String homepage() {
        return "views/web/index.html";
    }

}

package com.vietphat.newswave.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String redirectToHomepage() {
        return "redirect:trang-chu";
    }

    @GetMapping("/trang-chu")
    public String homepage() {
        return "views/web/index";
    }

}

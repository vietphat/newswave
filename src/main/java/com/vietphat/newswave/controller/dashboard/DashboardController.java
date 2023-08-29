package com.vietphat.newswave.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/quan-tri")
    public String dashboard() {
        return "views/dashboard/index";
    }

}

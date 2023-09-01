package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.UserDTO;
import com.vietphat.newswave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/quan-tri/nguoi-dung")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "offset", required = false, defaultValue = "5") Integer offset) {

        Pageable pageable = PageRequest.of(page - 1, offset);

        UserDTO userDTO = userService.findAll(pageable);

        model.addAttribute("model", userDTO);
        return "views/dashboard/user/list";
    }

    @GetMapping("/them")
    public String createForm(Model model) {

        model.addAttribute("model", new UserDTO());

        return "views/dashboard/user/edit";
    }

    @GetMapping("/chinh-sua/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {

        UserDTO userDTO = userService.findById(id);

        model.addAttribute("model", userDTO);

        return "views/dashboard/user/edit";
    }

}

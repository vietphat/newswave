package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.RoleDTO;
import com.vietphat.newswave.dto.user.UserDTO;
import com.vietphat.newswave.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/quan-tri/quyen")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        RoleDTO roleDTO = roleService.findAll(pageable, search);

        model.addAttribute("model", roleDTO);
        return "views/dashboard/role/list";
    }

    @GetMapping("/danh-sach/{id}")
    public String getDetails(Model model, @PathVariable("id") Long id) {

        RoleDTO roleDTO = roleService.findById(id);

        model.addAttribute("model", roleDTO);
        return "views/dashboard/role/details";
    }

}

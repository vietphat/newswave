package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.UserDTO;
import com.vietphat.newswave.enums.UserStatus;
import com.vietphat.newswave.service.RoleService;
import com.vietphat.newswave.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/quan-tri/nguoi-dung")
public class UserController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        UserDTO userDTO = userService.findAll(pageable, search);

        model.addAttribute("model", userDTO);
        return "views/dashboard/user/list";
    }

    @GetMapping("/danh-sach/{id}")
    public String getDetails(Model model, @PathVariable("id") Long id) {

        UserDTO userDTO = userService.findUserWithRolesById(id);

        model.addAttribute("model", userDTO);
        return "views/dashboard/user/details";
    }

    @GetMapping("/them")
    public String createForm(Model model) {

        model.addAttribute("model", new UserDTO());
        model.addAttribute("roles", roleService.findAll());
        return "views/dashboard/user/edit";
    }

    @PostMapping("/them")
    public String create(@Valid @ModelAttribute("model") UserDTO userDTO,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            return "views/dashboard/user/edit";
        }

        // gọi service thêm dữ liệu
        UserDTO createdUserDTO = userService.save(userDTO);

        if (createdUserDTO == null) {
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", "Đã xảy ra lỗi máy chủ");
            return "redirect:/quan-tri/nguoi-dung/danh-sach";
        }

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Thêm người dùng thành công");
        return "redirect:/quan-tri/nguoi-dung/danh-sach?page=" + createdUserDTO.getCurrentPage() + "&size=5";
    }

    @GetMapping("/chinh-sua/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {

        UserDTO userDTO = userService.findUserWithRolesById(id);

        model.addAttribute("model", userDTO);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("userStatus", UserStatus.values());
        return "views/dashboard/user/edit";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

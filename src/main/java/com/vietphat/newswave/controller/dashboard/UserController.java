package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.user.ResetPasswordDTO;
import com.vietphat.newswave.dto.user.UserDTO;
import com.vietphat.newswave.entity.UserEntity;
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
        return "views/dashboard/user/edit";
    }

    @PostMapping("/them")
    public String create(@Valid @ModelAttribute("model") UserDTO userDTO,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "views/dashboard/user/edit";
        }

        // gọi service thêm dữ liệu
        UserDTO createdUserDTO = userService.save(userDTO);

        if (createdUserDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi máy chủ");
            return "views/dashboard/user/edit";
        }

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Thêm người dùng thành công");
        return "redirect:/quan-tri/nguoi-dung/danh-sach?page=" + createdUserDTO.getCurrentPage() + "&size=5";
    }

    @GetMapping({"/chinh-sua/{id}", "/doi-mat-khau/{id}"})
    public String editForm(@PathVariable("id") Long id, Model model) {

        UserDTO userDTO = userService.findUserWithRolesById(id);

        model.addAttribute("model", userDTO);
        model.addAttribute("resetPasswordDTO", new ResetPasswordDTO());
        return "views/dashboard/user/edit";
    }

    @PostMapping("/chinh-sua/{id}")
    public String edit(@PathVariable("id") Long id,
                       @Valid @ModelAttribute("model") UserDTO userDTO,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors() && !bindingResult.hasFieldErrors("password")) {
            return "views/dashboard/user/edit";
        }

        UserDTO updatedUserDTO = userService.update(userDTO);

        if (updatedUserDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi máy chủ");
            return "views/dashboard/user/edit";
        }

        model.addAttribute("alert", "success");
        model.addAttribute("message", "Cập nhật người dùng thành công");
        model.addAttribute("model", updatedUserDTO);
        return "views/dashboard/user/edit";
    }

    @PostMapping("/doi-mat-khau/{id}")
    public String changePassword(@PathVariable("id") Long id,
                                 @Valid @ModelAttribute("resetPasswordDTO") ResetPasswordDTO resetPasswordDTO,
                                 BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("modalIsShown", true);
            model.addAttribute("model", userService.findUserWithRolesById(id));
            return "views/dashboard/user/edit";
        }

        UserDTO userDTO = userService.resetPassword(resetPasswordDTO);

        model.addAttribute("alert", "success");
        model.addAttribute("message", "Cập nhật mật khẩu thành công");
        model.addAttribute("model", userDTO);
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

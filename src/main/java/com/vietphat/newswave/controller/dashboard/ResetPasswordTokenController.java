package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.resetpasswordtoken.ResetPasswordTokenDTO;
import com.vietphat.newswave.service.ResetPasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/quan-tri/ma-tao-lai-mat-khau")
public class ResetPasswordTokenController {

    private ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    public ResetPasswordTokenController(ResetPasswordTokenService resetPasswordTokenService) {
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        ResetPasswordTokenDTO resetPasswordTokenDTO = resetPasswordTokenService.findAll(pageable, search);

        model.addAttribute("model", resetPasswordTokenDTO);
        return "views/dashboard/reset-password-token/list";
    }

    @GetMapping("/danh-sach/{id}")
    public String getDetails(Model model, @PathVariable("id") Long id) {

        ResetPasswordTokenDTO resetPasswordTokenDTO = resetPasswordTokenService.findById(id);

        model.addAttribute("model", resetPasswordTokenDTO);
        return "views/dashboard/reset-password-token/details";
    }

}

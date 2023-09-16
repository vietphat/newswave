package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.contact.ContactDTO;
import com.vietphat.newswave.dto.contact.ResponseDTO;
import com.vietphat.newswave.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quan-tri/lien-he")
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        ContactDTO contactDTO = contactService.searchContactsWithPagination(pageable, search);

        model.addAttribute("model", contactDTO);
        return "views/dashboard/contact/list";
    }

    @GetMapping({"/danh-sach/{id}", "/phan-hoi/{id}"})
    public String getDetails(Model model, @PathVariable("id") Long id) {

        ContactDTO contactDTO = contactService.findById(id);

        model.addAttribute("model", contactDTO);
        model.addAttribute("responseDTO", new ResponseDTO());
        return "views/dashboard/contact/details";
    }

    @PostMapping("/xoa")
    public String delete(@RequestParam(value = "selectedIds", required = false) String selectedIds,
                         RedirectAttributes redirectAttributes) {

        if (selectedIds == null) {
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn liên hệ cần xóa.");
            return "redirect:/quan-tri/lien-he/danh-sach";
        }

        String[] ids = selectedIds.split(",");

        contactService.delete(ids);

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Xóa liên hệ thành công.");
        return "redirect:/quan-tri/lien-he/danh-sach";
    }

    @PostMapping("/phan-hoi/{id}")
    public String response(@PathVariable("id") Long id,
                           @Valid @ModelAttribute("responseDTO") ResponseDTO responseDTO,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("model", contactService.findById(id));
            return "views/dashboard/contact/details";
        }

        ContactDTO contactDTO = contactService.response(responseDTO);

        if (contactDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi ở máy chủ.");
            model.addAttribute("model", contactService.findById(id));
            return "views/dashboard/contact/details";
        }

        model.addAttribute("alert", "success");
        model.addAttribute("message", "Gửi phản hồi thành công.");
        model.addAttribute("model", contactDTO);
        return "views/dashboard/contact/details";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}

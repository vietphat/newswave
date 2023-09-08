package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.tag.TagDTO;
import com.vietphat.newswave.service.TagService;
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
@RequestMapping("/quan-tri/the")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        TagDTO tagDTO = tagService.searchTagsWithPagination(pageable, search);

        model.addAttribute("model", tagDTO);
        return "views/dashboard/tag/list";
    }

    @GetMapping("/danh-sach/{id}")
    public String getDetails(@PathVariable("id") Long id, Model model) {

        TagDTO tagDTO = tagService.findById(id);

        model.addAttribute("model", tagDTO);
        return "views/dashboard/tag/details";
    }

    @GetMapping("/them")
    public String createForm(Model model) {

        model.addAttribute("model", new TagDTO());
        return "views/dashboard/tag/edit";
    }

    @PostMapping("/them")
    public String create(@Valid @ModelAttribute("model") TagDTO tagDTO,
                         BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "views/dashboard/tag/edit";
        }

        TagDTO createdTagDTO = tagService.save(tagDTO);

        if (createdTagDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi ở máy chủ");
            return "views/dashboard/tag/edit";
        }

        redirectAttributes.addFlashAttribute("model", createdTagDTO);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Thêm thẻ bài viết thành công");
        return "redirect:/quan-tri/the/danh-sach?page=" + createdTagDTO.getCurrentPage();
    }

    @GetMapping("/chinh-sua/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {

        TagDTO tagDTO = tagService.findById(id);

        model.addAttribute("model", tagDTO);
        return "views/dashboard/tag/edit";
    }

    @PostMapping("/chinh-sua/{id}")
    public String edit(@PathVariable("id") Long id,
                       @Valid @ModelAttribute("model") TagDTO tagDTO,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return "views/dashboard/tag/edit";
        }

        TagDTO updatedTagDTO = tagService.update(tagDTO);

        if (updatedTagDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi máy chủ");
            return "views/dashboard/tag/edit";
        }

        model.addAttribute("alert", "success");
        model.addAttribute("message", "Cập nhật thẻ bài viết thành công");
        model.addAttribute("model", updatedTagDTO);
        return "views/dashboard/tag/edit";
    }

    @PostMapping("/xoa")
    public String delete(@RequestParam(value = "selectedIds", required = false) String selectedIds,
                         RedirectAttributes redirectAttributes) {

        if (selectedIds == null) {
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn thẻ cần xóa.");
            return "redirect:/quan-tri/the/danh-sach";
        }

        String[] ids = selectedIds.split(",");

        tagService.delete(ids);

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Xóa thẻ bài viết thành công.");
        return "redirect:/quan-tri/the/danh-sach";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        // trim string input, if input is empty -> trim to null
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}

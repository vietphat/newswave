package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.category.CategoryDTO;
import com.vietphat.newswave.dto.user.UserDTO;
import com.vietphat.newswave.service.CategoryService;
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
@RequestMapping("/quan-tri/danh-muc")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        CategoryDTO categoryDTO = categoryService.searchCategoriesWithPagination(pageable, search);

        model.addAttribute("model", categoryDTO);
        return "views/dashboard/category/list";
    }

    @GetMapping("/danh-sach/{id}")
    public String getDetails(@PathVariable("id") Long id, Model model) {

        CategoryDTO categoryDTO = categoryService.findById(id);

        model.addAttribute("model", categoryDTO);
        return "views/dashboard/category/details";
    }

    @GetMapping("/them")
    public String createForm(Model model) {

        model.addAttribute("model", new CategoryDTO());
        return "views/dashboard/category/edit";
    }

    @PostMapping("/them")
    public String create(@Valid @ModelAttribute("model") CategoryDTO categoryDTO,
                         BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "views/dashboard/category/edit";
        }

        CategoryDTO createdCategoryDTO = categoryService.save(categoryDTO);

        if (categoryDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi ở máy chủ");
            return "views/dashboard/category/edit";
        }

        redirectAttributes.addFlashAttribute("model", createdCategoryDTO);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Thêm danh mục thành công");
        return "redirect:/quan-tri/danh-muc/danh-sach?page=" + createdCategoryDTO.getCurrentPage();
    }

    @GetMapping("/chinh-sua/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {

        CategoryDTO categoryDTO = categoryService.findById(id);

        model.addAttribute("model", categoryDTO);
        return "views/dashboard/category/edit";
    }

    @PostMapping("/chinh-sua/{id}")
    public String edit(@PathVariable("id") Long id,
                       @Valid @ModelAttribute("model") CategoryDTO categoryDTO,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {

            return "views/dashboard/category/edit";
        }

        CategoryDTO updatedCategoryDTO = categoryService.update(categoryDTO);

        if (updatedCategoryDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi máy chủ");
            return "views/dashboard/category/edit";
        }

        model.addAttribute("alert", "success");
        model.addAttribute("message", "Cập nhật danh mục thành công");
        model.addAttribute("model", updatedCategoryDTO);
        return "views/dashboard/category/edit";
    }

    @PostMapping("/xoa")
    public String delete(@RequestParam(value = "selectedIds", required = false) String selectedIds,
                         RedirectAttributes redirectAttributes) {

        if (selectedIds == null) {
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn danh mục cần xóa.");
            return "redirect:/quan-tri/danh-muc/danh-sach";
        }

        String[] ids = selectedIds.split(",");

        categoryService.delete(ids);

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công.");
        return "redirect:/quan-tri/danh-muc/danh-sach";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        // trim string input, if input is empty -> trim to null
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}

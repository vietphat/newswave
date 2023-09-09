package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.constant.SystemConstant;
import com.vietphat.newswave.dto.post.PostDTO;
import com.vietphat.newswave.service.CategoryService;
import com.vietphat.newswave.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/quan-tri/bai-viet")
public class PostController {

    private PostService postService;

    private CategoryService categoryService;

    @Autowired
    public PostController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        PostDTO postDTO = postService.searchPostsWithPagination(pageable, search);

        model.addAttribute("model", postDTO);
        return "views/dashboard/post/list";
    }

    @GetMapping("/them")
    public String createForm(Model model) {

        PostDTO postDTO = new PostDTO();

        model.addAttribute("model", postDTO);
        model.addAttribute("categories", categoryService.findAll());
        return "views/dashboard/post/edit";
    }

    @PostMapping("/them")
    public String create(@Valid @ModelAttribute("model") PostDTO postDTO,
                         @RequestParam("thumbnailFile") MultipartFile multipartFile,
                         BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "views/dashboard/post/edit";
        }

        PostDTO createdPostDTO;
        if (!multipartFile.isEmpty()) {
            // xử lý lưu file
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            // lưu bài viết
            postDTO.setThumbnail(fileName);
            createdPostDTO = postService.save(postDTO);

            // lưu file
            String uploadDir = SystemConstant.IMAGE_PATH + "/post-thumbnails/" + createdPostDTO.getId();

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } else {
            createdPostDTO = postService.save(postDTO);
        }


        if (createdPostDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi máy chủ");
            return "views/dashboard/post/edit";
        }

        redirectAttributes.addFlashAttribute("model", postDTO);
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Thêm bài viết thành công.");
        return "redirect:/quan-tri/bai-viet/danh-sach?page=" + createdPostDTO.getCurrentPage();
    }

    @GetMapping("/chinh-sua/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {

        PostDTO postDTO = postService.findById(id);

        model.addAttribute("model", postDTO);
        model.addAttribute("categories", categoryService.findAll());
        return "views/dashboard/post/edit";
    }

    @PostMapping("/chinh-sua/{id}")
    public String edit(@PathVariable("id") Long id,
                       @Valid @ModelAttribute("model") PostDTO postDTO,
                       @RequestParam("thumbnailFile") MultipartFile multipartFile,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "views/dashboard/post/edit";
        }

        PostDTO updatedPostDTO;
        if (!multipartFile.isEmpty()) {
            // xử lý lưu file
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            // lưu bài viết
            postDTO.setThumbnail(fileName);
            updatedPostDTO = postService.update(postDTO);

            // lưu file
            String uploadDir = SystemConstant.IMAGE_PATH + "/post-thumbnails/" + updatedPostDTO.getId();

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } else {
            updatedPostDTO = postService.update(postDTO);
        }

        if (updatedPostDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi máy chủ.");
            return "views/dashboard/post/edit";
        }

        model.addAttribute("model", updatedPostDTO);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("alert", "success");
        model.addAttribute("message", "Cập nhật bài viết thành công.");
        return "views/dashboard/post/edit";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

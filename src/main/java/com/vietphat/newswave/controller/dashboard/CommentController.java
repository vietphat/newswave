package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.dto.comment.CommentDTO;
import com.vietphat.newswave.dto.role.RoleDTO;
import com.vietphat.newswave.dto.user.UserDTO;
import com.vietphat.newswave.enums.CommentStatus;
import com.vietphat.newswave.service.CommentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quan-tri/binh-luan")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        CommentDTO commentDTO = commentService.searchCommentsWithPagination(pageable, search);

        model.addAttribute("model", commentDTO);
        return "views/dashboard/comment/list";
    }

    @GetMapping({"/danh-sach/{id}", "/thay-doi-trang-thai/{id}"})
    public String getDetails(Model model, @PathVariable("id") Long id) {

        CommentDTO commentDTO = commentService.findDetailsById(id);

        model.addAttribute("model", commentDTO);
        return "views/dashboard/comment/details";
    }

    @PostMapping("/xoa")
    public String delete(@RequestParam(value = "selectedIds", required = false) String selectedIds,
                         RedirectAttributes redirectAttributes) {

        if (selectedIds == null) {
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn bình luận cần xóa.");
            return "redirect:/quan-tri/binh-luan/danh-sach";
        }

        String[] ids = selectedIds.split(",");

        commentService.delete(ids);

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Xóa bình luận thành công.");
        return "redirect:/quan-tri/binh-luan/danh-sach";
    }

    @PostMapping("/thay-doi-trang-thai/{id}")
    public String changeStatus(@PathVariable("id") Long id,
                               @RequestParam(value = "status") CommentStatus status,
                               Model model) {

        CommentDTO commentDTO = commentService.changeStatus(id, status);

        if (commentDTO == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi ở máy chủ.");
        }

        model.addAttribute("alert", "success");
        model.addAttribute("message", "Đã thay đổi trạng thái bình luận thành " + commentDTO.getStatus().name());
        model.addAttribute("model", commentDTO);
        return "views/dashboard/comment/details";
    }
}

package com.vietphat.newswave.controller.dashboard;

import com.vietphat.newswave.constant.SystemConstant;
import com.vietphat.newswave.dto.commentreport.CommentReportDTO;
import com.vietphat.newswave.service.CommentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quan-tri/bao-cao-binh-luan")
public class CommentReportController {

    private CommentReportService commentReportService;

    @Autowired
    public CommentReportController(CommentReportService commentReportService) {
        this.commentReportService = commentReportService;
    }


    @GetMapping("/danh-sach")
    public String getList(Model model,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(value = "search", required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size);

        CommentReportDTO commentReportDTO = commentReportService.searchCommentReportsWithPagination(pageable, search);

        model.addAttribute("model", commentReportDTO);
        return "views/dashboard/comment-report/list";
    }

    @GetMapping({"/danh-sach/{id}", "/kiem-duyet/{id}"})
    public String getDetails(Model model, @PathVariable("id") Long id) {

        CommentReportDTO commentReportDTO = commentReportService.findDetailsById(id);

        model.addAttribute("model", commentReportDTO);
        return "views/dashboard/comment-report/details";
    }

    @PostMapping("/xoa")
    public String delete(@RequestParam(value = "selectedIds", required = false) String selectedIds,
                         RedirectAttributes redirectAttributes) {

        if (selectedIds == null) {
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn báo cáo bình luận cần xóa.");
            return "redirect:/quan-tri/bao-cao-binh-luan/danh-sach";
        }

        String[] ids = selectedIds.split(",");

        commentReportService.delete(ids);

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Xóa báo cáo bình luận thành công.");
        return "redirect:/quan-tri/bao-cao-binh-luan/danh-sach";
    }

    @PostMapping("/kiem-duyet/{id}")
    public String approve(@PathVariable("id") Long id,
                          @RequestParam("action") String action,
                          Model model) {

        CommentReportDTO commentReportDTO = commentReportService.approveReport(id, action);

        if (commentReportDTO == null) {
            model.addAttribute("model", commentReportService.findDetailsById(id));
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Đã xảy ra lỗi ở máy chủ.");

            return "views/dashboard/comment-report/details";
        }

        String message = action.equals(SystemConstant.CONFIRM_COMMENT_REPORT)
                ? "Xác nhận bình luận vi phạm thành công. Bình luận đã bị ẩn."
                : "Xác nhận báo cáo bình luận chưa phù hợp thành công.";

        model.addAttribute("model", commentReportService.findDetailsById(id));
        model.addAttribute("alert", "success");
        model.addAttribute("message", message);
        return "views/dashboard/comment-report/details";
    }
}

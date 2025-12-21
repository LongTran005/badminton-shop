package com.badmintonshop.controller.admin;

import com.badmintonshop.repository.OrderItemRepository;
import com.badmintonshop.repository.StaffRepository;
import com.badmintonshop.service.StringingBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/stringing")
@RequiredArgsConstructor
public class AdminStringingController {

    private final OrderItemRepository orderItemRepository;
    private final StaffRepository staffRepository;
    private final StringingBookingService bookingService;

    @GetMapping
    public String listRequests(Model model,
            @PageableDefault(size = 20, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("items", orderItemRepository.findByHasStringingServiceTrue(pageable));
        // Need to filter staff who are role = STRINGING_STAFF
        model.addAttribute("staffs", staffRepository.findAll());
        return "admin/stringing/list";
    }

    @PostMapping("/assign")
    public String assignStaff(@RequestParam Long orderItemId,
            @RequestParam Long staffId,
            RedirectAttributes redirectAttributes) {
        try {
            bookingService.assignStaff(orderItemId, staffId);
            redirectAttributes.addFlashAttribute("success", "Assigned successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/stringing";
    }

    @PostMapping("/qc-approve")
    public String approveQuality(@RequestParam Long orderItemId, RedirectAttributes redirectAttributes) {
        try {
            bookingService.approveQuality(orderItemId);
            redirectAttributes.addFlashAttribute("success", "Quality Checked & Approved");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/stringing";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("workload", bookingService.getStaffWorkload());
        return "admin/stringing/dashboard";
    }
}

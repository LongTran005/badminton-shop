package com.badmintonshop.controller.staff;

import com.badmintonshop.entity.Staff;
import com.badmintonshop.entity.enums.StringingStatus;
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

import java.security.Principal;

@Controller
@RequestMapping("/staff/stringing")
@RequiredArgsConstructor
public class StaffStringingController {

    private final OrderItemRepository orderItemRepository;
    private final StaffRepository staffRepository;
    private final StringingBookingService bookingService;

    @GetMapping
    public String dashboard(Model model, Principal principal, @PageableDefault(size = 20) Pageable pageable) {
        String email = principal.getName();
        Staff staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        model.addAttribute("assignedItems",
                orderItemRepository.findByAssignedStaffAndStringingStatus(staff, StringingStatus.ASSIGNED, pageable));

        model.addAttribute("inProgressItems",
                orderItemRepository.findByAssignedStaffAndStringingStatus(staff, StringingStatus.IN_PROGRESS,
                        pageable));

        return "staff/stringing/dashboard";
    }

    @PostMapping("/start")
    public String start(@RequestParam Long orderItemId) {
        bookingService.startStringing(orderItemId);
        return "redirect:/staff/stringing";
    }

    @PostMapping("/complete")
    public String complete(@RequestParam Long orderItemId) {
        bookingService.completeStringing(orderItemId);
        return "redirect:/staff/stringing";
    }
}

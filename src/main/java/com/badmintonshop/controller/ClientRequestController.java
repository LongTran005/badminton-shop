package com.badmintonshop.controller;

import com.badmintonshop.dto.request.ExchangeRequest;
import com.badmintonshop.dto.request.WarrantyRequest;
import com.badmintonshop.entity.enums.ExchangeReason;
import com.badmintonshop.entity.enums.IssueType;
import com.badmintonshop.service.ExchangeService;
import com.badmintonshop.service.WarrantyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account/requests")
@RequiredArgsConstructor
public class ClientRequestController {

    private final ExchangeService exchangeService;
    private final WarrantyService warrantyService;

    @GetMapping
    public String listRequests(Model model) {
        // In a real app, verify user ownership. fetching all for now or filter by
        // current user if I had User ID
        // Simplified: Fetch all pending requests for demo or mock getting my requests
        // For proper implementation, Service needs method findByUserId
        return "account/requests/index";
    }

    @GetMapping("/exchange/new")
    public String newExchangeForm(@RequestParam Long orderItemId, Model model) {
        ExchangeRequest request = new ExchangeRequest();
        request.setOrderItemId(orderItemId);
        model.addAttribute("exchangeRequest", request);
        model.addAttribute("reasons", ExchangeReason.values());
        return "account/requests/create_exchange";
    }

    @PostMapping("/exchange")
    public String createExchange(@ModelAttribute ExchangeRequest request, RedirectAttributes redirectAttributes) {
        try {
            exchangeService.createExchange(request);
            redirectAttributes.addFlashAttribute("successMessage", "Exchange request submitted successfully.");
            return "redirect:/account/requests";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/account/requests/exchange/new?orderItemId=" + request.getOrderItemId();
        }
    }

    @GetMapping("/warranty/new")
    public String newWarrantyForm(@RequestParam Long orderItemId, Model model) {
        WarrantyRequest request = new WarrantyRequest();
        request.setOrderItemId(orderItemId);
        model.addAttribute("warrantyRequest", request);
        model.addAttribute("issueTypes", IssueType.values());
        return "account/requests/create_warranty";
    }

    @PostMapping("/warranty")
    public String createWarranty(@ModelAttribute WarrantyRequest request, RedirectAttributes redirectAttributes) {
        try {
            warrantyService.createWarranty(request);
            redirectAttributes.addFlashAttribute("successMessage", "Warranty claim submitted successfully.");
            return "redirect:/account/requests";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/account/requests/warranty/new?orderItemId=" + request.getOrderItemId();
        }
    }
}

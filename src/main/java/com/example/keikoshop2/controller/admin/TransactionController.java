package com.example.keikoshop2.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.keikoshop2.model.Transactions;
import com.example.keikoshop2.service.IPaymentService;
import com.example.keikoshop2.service.IProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@PreAuthorize("hasRole('admin')")
@RequiredArgsConstructor
@RequestMapping("/admin/transactions")
public class TransactionController {
  private final IPaymentService paymentService;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @GetMapping
  public ResponseEntity<List<Transactions>> getAllTransactions() {
    return new ResponseEntity<>(paymentService.getAllTransactions(), HttpStatus.FOUND);
  }

  @GetMapping("/manage-transactions")
  public String manageTransactions(Model model) {
    List<Transactions> transactions = paymentService.getAllTransactions();
    model.addAttribute("transactions", transactions);
    return "admin/transactions";
  }

  @GetMapping("/confirm/{orderId}")
  public String confirmPayment(@PathVariable String orderId, RedirectAttributes redirectAttributes) {
    // Tinggal bikin notifikasi aja
    try {
      paymentService.confirmPayment(orderId);
      redirectAttributes.addFlashAttribute("successMessage", "Payment confirmed successfully");
    } catch (Exception e) {
      logger.error(e.getMessage());
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/admin/transactions/manage-transactions";
  }

  @GetMapping("/decline/{orderId}")
  public String declinePayment(@PathVariable String orderId, RedirectAttributes redirectAttributes) {
    // Tinggal bikin notifikasi aja
    try {
      paymentService.declinePayment(orderId);
      redirectAttributes.addFlashAttribute("successMessage", "Payment declined successfully");
    } catch (Exception e) {
      logger.error(e.getMessage());
      redirectAttributes.addFlashAttribute("errorMessage", "Failed to decline payment");
    }
    return "redirect:/admin/transactions/manage-transactions";
  }

}

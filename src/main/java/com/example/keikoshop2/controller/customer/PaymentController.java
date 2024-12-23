package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Review;
import com.example.keikoshop2.model.Transactions;
import com.example.keikoshop2.model.User;
import com.example.keikoshop2.model.Voucher;
import com.example.keikoshop2.service.ICartService;
import com.example.keikoshop2.service.IPaymentService;
import com.example.keikoshop2.service.IReviewService;
import lombok.RequiredArgsConstructor;

//Service
import com.example.keikoshop2.service.IUserService;
import com.example.keikoshop2.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

  private final IPaymentService paymentService;
  private final ICartService cartService;
  private final UserService userService;
  private final ObjectMapper objectMapper;

  private boolean checkIfUserIsAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"));
  }

  @GetMapping
  public ResponseEntity<List<Transactions>> getAllTransactions() {
    return new ResponseEntity<>(paymentService.getAllTransactions(), HttpStatus.FOUND);
  }

  @GetMapping("/{orderId}")
  public String viewPaymentByOrderId(Model model, @PathVariable String orderId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    User user = userService.findByEmail(email);
    int userId = user.getId();

    List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
    Transactions transaction = paymentService.getTransactionsByOrderId(orderId);

    boolean isAdmin = checkIfUserIsAdmin();
    if (transaction.getUserId() != userId) {
      return "redirect:/home";
    }

    model.addAttribute("transaction", transaction);
    model.addAttribute("cartItems", cartItems);
    model.addAttribute("totalPrice", transaction.getTotalPrice());
    model.addAttribute("user", user);
    model.addAttribute("isAdmin", isAdmin);

    return "customer/payment";
  }

  @PostMapping("/{orderId}/uploadPaymentProof")
  public String uploadPaymentProof(@PathVariable String orderId, @RequestParam("imageFiles") MultipartFile image,
      RedirectAttributes redirectAttributes) {
    try {
      Transactions transaction = paymentService.getTransactionsByOrderId(orderId);

      if (transaction.IsPaid()) {
        redirectAttributes.addFlashAttribute("errorMessage", "Payment proof already uploaded");
        return "redirect:/pesanan-saya";
      }

      if (transaction.isCancelled()) {
        redirectAttributes.addFlashAttribute("errorMessage",
            "Payment proof cannot be uploaded because the transaction has been cancelled");
        return "redirect:/pesanan-saya";
      }

      paymentService.uploadPaymentProof(transaction, image);
      redirectAttributes.addFlashAttribute("successMessage", "Payment proof successfully uploaded");
      return "redirect:/pesanan-saya";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      return "redirect:/payment/" + orderId;
    }
  }

  @PostMapping("/checkout")
  public String checkoutTransaction(@ModelAttribute Transactions transaction,
      @RequestParam(required = false) String voucherCode, RedirectAttributes redirectAttributes) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String email = authentication.getName();
      User user = userService.findByEmail(email);
      int userId = user.getId();

      List<Cart> cart = cartService.getCartItemsByUserId(userId);

      // keamamanan
      if (cart.isEmpty()) {
        redirectAttributes.addFlashAttribute("errorMessage", "Cart is empty");
        return "redirect:/cart/my";
      }

      List<Integer> cartIds = cart.stream().map(Cart::getId).collect(Collectors.toList());
      String cartIdsJson = objectMapper.writeValueAsString(cartIds);

      transaction.setTotalPrice(cartService.getTotalPriceByUserId(userId));

      if (voucherCode != null && !voucherCode.isEmpty()) {
        Voucher voucher = cartService.redeemVoucher(voucherCode);
        transaction.setUsingPromo(true);
        transaction.setPromoDiscount(voucher.getDiscount());
        transaction
            .setTotalPrice(transaction.getTotalPrice() - (transaction.getTotalPrice() * voucher.getDiscount()) / 100);
      }

      transaction.setUserId(userId);
      transaction.setCart_ids(cartIdsJson);
      paymentService.checkoutTransactions(transaction);
      redirectAttributes.addFlashAttribute("successMessage", "Payment successfully added");
      return "redirect:/payment/" + transaction.getOrderId();
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      return "redirect:/cart/my";
    }
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Transactions>> getTransactionsByUserId(@PathVariable int userId) {
    return new ResponseEntity<>(paymentService.getTransactionsByUserId(userId), HttpStatus.FOUND);
  }

  //Api Route
  
}

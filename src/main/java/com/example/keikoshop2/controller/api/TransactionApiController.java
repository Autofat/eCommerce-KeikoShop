package com.example.keikoshop2.controller.api;

import ch.qos.logback.core.model.Model;
import io.micrometer.core.ipc.http.HttpSender.Response;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.model.Transactions;
import com.example.keikoshop2.service.IPaymentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/transactions")
public class TransactionApiController {

  @Autowired
  private IPaymentService paymentService;

  @GetMapping
  public ResponseEntity<List<Transactions>> getAllTransactions() {
    return new ResponseEntity<>(paymentService.getAllTransactions(), HttpStatus.FOUND);
  }

  @GetMapping("/{orderId}/cart")
  @ResponseBody
  public ResponseEntity<List<Cart>> getCartByOrderId(@PathVariable String orderId) {
    try {
      List<Cart> transactions = paymentService.getCartByTransactionOrderId(orderId);
      return new ResponseEntity<>(transactions, HttpStatus.FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/{orderId}/products")
  @ResponseBody
  public ResponseEntity<List<Product>> getProductsByOrderId(@PathVariable String orderId) {
    try {
      List<Product> transactions = paymentService.getProductsByTransactionOrderId(orderId);
      return new ResponseEntity<>(transactions, HttpStatus.FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}

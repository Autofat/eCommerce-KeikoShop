package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.model.Transactions;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPaymentService {
  List<Transactions> getAllTransactions();

  List<Transactions> getTransactionsByUserId(int userId);

  List<Product> getProductsByTransactionOrderId(String orderId);

  Boolean isOrderAlreadyReviewed(String orderId);

  List<Cart> getCartByTransactionOrderId(String orderId);

  Transactions getTransactionsByOrderId(String orderId);

  Transactions checkoutTransactions(Transactions transaction);

  Transactions uploadPaymentProof(Transactions transaction, MultipartFile image);

  void confirmPayment(String orderId);

  void declinePayment(String orderId);

  void setIsRating(String orderId);

  // Transactions updateTransactions(Transactions transaction, int id,
  // MultipartFile image);

  // void cancelPayment(int id);

  // void confirmPaymentByAdmin(int id);
}

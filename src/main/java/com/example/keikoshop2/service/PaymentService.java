package com.example.keikoshop2.service;

//Exception
import com.example.keikoshop2.exception.ProductNotFoundExeption;
import com.example.keikoshop2.exception.ProductAlreadyExistsExeption;

//Model
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Transactions;

//Repository
import com.example.keikoshop2.repository.ProductRepository;
import com.example.keikoshop2.repository.TransactionRepository;
import com.example.keikoshop2.repository.CartRepository;

//Library
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

  @Autowired
  private final TransactionRepository transactionRepository;
  private final CartRepository cartRepository;
  private final String uploadDir = "src/main/resources/static/images/transaction/paymentProof/";
  private final IProductService productService;
  private final ICartService cartService;

  private final ObjectMapper objectMapper;

  // All User
  @Override
  public List<Transactions> getAllTransactions() {
    List<Transactions> transactions = transactionRepository.findAll();
    for (Transactions transaction : transactions) {
      try {
        List<Integer> cartIds = objectMapper.readValue(transaction.getCart_ids(), List.class);
        List<Cart> cartItems = cartService.getCartItemsByCartIds(cartIds);
        List<Product> products = cartItems.stream()
            .map(cart -> productService.getProductById(cart.getProduct().getId()))
            .collect(Collectors.toList());
        transaction.setProducts(products);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return transactions;
  }

  @Override
  public List<Transactions> getTransactionsByUserId(int userId) {
    List<Transactions> transactions = transactionRepository.findByUserId(userId);
    for (Transactions transaction : transactions) {
      try {
        List<Integer> cartIds = objectMapper.readValue(transaction.getCart_ids(), List.class);
        List<Cart> cartItems = cartService.getCartItemsByCartIds(cartIds);
        List<Product> products = cartItems.stream()
            .map(cart -> productService.getProductById(cart.getProduct().getId()))
            .collect(Collectors.toList());
        transaction.setProducts(products);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return transactions;
  }

  @Override
  public List<Product> getProductsByTransactionOrderId(String orderId) {
    Transactions transaction = getTransactionsByOrderId(orderId);
    try {
      List<Integer> cartIds = objectMapper.readValue(transaction.getCart_ids(), List.class);
      List<Cart> cartItems = cartService.getCartItemsByCartIds(cartIds);
      return cartItems.stream().map(cart -> productService.getProductById(cart.getProduct().getId()))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return List.of();
    }
  }

  @Override
  public List<Cart> getCartByTransactionOrderId(String orderId) {
    Transactions transaction = getTransactionsByOrderId(orderId);
    try {
      List<Integer> cartIds = objectMapper.readValue(transaction.getCart_ids(), List.class);
      return cartService.getCartItemsByCartIds(cartIds);
    } catch (Exception e) {
      e.printStackTrace();
      return List.of();
    }
  }

  @Override
  public Transactions getTransactionsByOrderId(String orderId) {
    Optional<Transactions> transaction = transactionRepository.findByOrderId(orderId);
    return transaction.orElseThrow(() -> new ProductNotFoundExeption(orderId + " Not found"));
  }

  @Override
  public Boolean isOrderAlreadyReviewed(String orderId) {
    Transactions transaction = getTransactionsByOrderId(orderId);
    return transaction.isRating();
  }

  @Override
  public void setIsRating(String orderId) {
    Transactions transaction = getTransactionsByOrderId(orderId);
    transaction.setIsRating(true);
    transactionRepository.save(transaction);
  }

  // Admin Only
  @Override
  public void confirmPayment(String orderId) {
    try {
      Transactions transaction = getTransactionsByOrderId(orderId);
      if (transaction.isConfirmed() || transaction.isCancelled()) {
        throw new IllegalArgumentException("Transaction already confirmed or cancelled");
      }
      transaction.setConfirmed(true);
      transactionRepository.save(transaction);
    } catch (Exception e) {
      throw new ProductNotFoundExeption(e.getMessage());
    }
  }

  @Override
  public void declinePayment(String orderId) {
    try {
      Transactions transaction = getTransactionsByOrderId(orderId);
      if (transaction.isConfirmed() || transaction.isCancelled()) {
        throw new IllegalArgumentException("Transaction already confirmed or cancelled");
      }
      transaction.setCancelled(true);
      transactionRepository.save(transaction);
    } catch (Exception e) {
      throw new ProductNotFoundExeption(e.getMessage());
    }
  }

  // User Only
  @Override
  public Transactions checkoutTransactions(Transactions transaction) {
    if (transactionRepository.existsByOrderId(transaction.getOrderId())) {
      throw new ProductAlreadyExistsExeption(transaction.getOrderId() + " Already exists");
    }
    // delete cartnya dahulu ketika checkout dan pending pembayaran
    List<Cart> cart = cartRepository.findByUserIdAndIsDeletedFalse(transaction.getUserId());
    this.deleteCarts(cart);

    return transactionRepository.save(transaction);
  }

  @Override
  public Transactions uploadPaymentProof(Transactions transaction, MultipartFile image) {
    if (image == null || image.isEmpty() || image.getSize() == 0 || image.getOriginalFilename() == null
        || image.getOriginalFilename().trim().isEmpty()) {
      throw new IllegalArgumentException("No valid image file provided");
    }

    try {
      if (transaction.getPaymentProofImage() != null) {
        Path oldImagePath = Paths.get(uploadDir + transaction.getPaymentProofImage());
        Files.deleteIfExists(oldImagePath);
      }
      String imageName = saveImage(image);
      transaction.setPaymentProofImage(imageName);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

    transaction.setPaid(true);
    return transactionRepository.save(transaction);
  }

  private String saveImage(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      throw new IOException("File is empty");
    }

    if (file.getSize() > 2 * 1024 * 1024) { // Check if file size is greater than 2MB
      throw new IOException("File size exceeds the maximum limit of 2MB");
    }

    String mimeType = file.getContentType();
    if (mimeType == null || !mimeType.startsWith("image/")) {
      throw new IOException("Invalid file type. Only image files are allowed.");
    }

    String originalFileName = file.getOriginalFilename();
    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
    String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

    Path path = Paths.get(uploadDir + uniqueFileName);
    Files.createDirectories(path.getParent());
    Files.write(path, file.getBytes());

    return uniqueFileName;
  }

  private void deleteCarts(List<Cart> cart) {
    for (Cart c : cart) {
      c.setDeleted(true);
      cartRepository.save(c);
    }
  }
}

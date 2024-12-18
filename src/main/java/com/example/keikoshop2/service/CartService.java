package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.model.Voucher;

import com.example.keikoshop2.repository.CartRepository;
import com.example.keikoshop2.repository.VoucherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    private final VoucherRepository voucherRepository;

    @Autowired
    public CartService(CartRepository cartRepository, VoucherRepository voucherRepository) {
        this.cartRepository = cartRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<Cart> getCartItemsByUserId(int userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void addToCart(int userId, Product product, int quantity) {
        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndProductId(userId, product.getId());
        if (existingCartItem.isPresent()) {
            Cart cart = existingCartItem.get();
            int newQuantity = cart.getQuantity() + quantity;
            if (newQuantity > product.getStock()) {
                newQuantity = product.getStock();
            }
            cart.setQuantity(newQuantity);
            cart.setTotalPrice(cart.getQuantity() * product.getPrice());
            cartRepository.save(cart);
        } else {
            if (product.getStock() < quantity) {
                quantity = product.getStock();
            }
            if (quantity <= 0) {
                return;
            }

            int finalQuantity = Math.min(quantity, product.getStock());
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProduct(product);
            cart.setQuantity(finalQuantity);
            cart.setTotalPrice(finalQuantity * product.getPrice());
            cartRepository.save(cart);
        }
    }

    public double getTotalPriceByUserId(int userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        return cartItems.stream()
                .mapToDouble(Cart::getTotalPrice)
                .sum();
    }

    @Override
    public Cart getCartItemById(int cartId) {
        Optional<Cart> cartItem = cartRepository.findById(cartId);
        return cartItem.orElse(null);
    }

    @Override
    public void updateQuantity(int cartId, int quantity) {
        Optional<Cart> cartItem = cartRepository.findById(cartId);
        if (cartItem.isPresent()) {
            Cart cart = cartItem.get();
            Product product = cart.getProduct();
            if (quantity > product.getStock() && quantity > 0) {
                quantity = cart.getQuantity(); // biar ga melebihi stock, ambil stock asli aja
                                               // dengan menambahkan quantity yang sudah ada
            }
            cart.setQuantity(quantity);
            cart.setTotalPrice(quantity * product.getPrice());

            // product.setStock((product.getStock() + original_quantity) - quantity); gaguna
            // cuy mungkin nanti dipake di konfirmasi pembayaran

            cartRepository.save(cart);
        }
    }

    @Override
    public void removeFromCart(int cartId) {
        // add stock to product
        Optional<Cart> cartItem = cartRepository.findById(cartId);
        if (cartItem.isPresent()) {
            Cart cart = cartItem.get();
            Product product = cart.getProduct();
            product.setStock(product.getStock() + cart.getQuantity());
            cartRepository.delete(cart); // ga perlu pake deleteById karena udh dapet dari atasnya
        }
    }

    @Override
    public Voucher redeemVoucher(String voucherCode) {
        Optional<Voucher> voucher = voucherRepository.findByCode(voucherCode);
        if (voucher.isPresent() && voucher.get().getIsValid()) {
            return voucher.get();
        }
        throw new IllegalArgumentException("Invalid voucher code");
    }
}
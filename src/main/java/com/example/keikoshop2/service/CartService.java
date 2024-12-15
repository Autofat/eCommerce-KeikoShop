package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
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
    public void removeFromCart(int cartId) {
        cartRepository.deleteById(cartId);
    }
}
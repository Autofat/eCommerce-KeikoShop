package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.ICartService;
import com.example.keikoshop2.service.IProductService;
import com.example.keikoshop2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ICartService cartService;
    private final IProductService productService;
    private final UserService userService;

    private boolean checkIfUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"));
    }

    @Autowired
    public CartController(ICartService cartService, IProductService productService, UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/my")
    public String viewCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        int userId = user.getId();
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
        double totalPrice = cartService.getTotalPriceByUserId(userId);
        boolean isAdmin = checkIfUserIsAdmin();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("totalPrice", totalPrice);
        return "customer/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        int userId = userService.findByEmail(email).getId();
        Product product = productService.getProductById(productId);
        cartService.addToCart(userId, product, quantity);
        return "redirect:/cart/my";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("cartId") int cartId) {
        cartService.removeFromCart(cartId);
        return "redirect:/cart/my";
    }
}
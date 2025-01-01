package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.ICartService;
import com.example.keikoshop2.service.IProductService;
import com.example.keikoshop2.service.ReviewService;
import com.example.keikoshop2.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faq")
public class FaqController {

    private final UserService userService;
    private final ICartService cartService;
    private final IProductService productService;

    @Autowired
    public FaqController(UserService userService,ICartService cartService,IProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }

    private boolean checkIfUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"));
    }

    @GetMapping
    public String showFAQ(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        boolean isAdmin = checkIfUserIsAdmin();
        int userId = user.getId();
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
        List<Product> products = productService.getAllProducts();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("products", products);
        model.addAttribute("user", user);
        return "customer/faq";
    }
}
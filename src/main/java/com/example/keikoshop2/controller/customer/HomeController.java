package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.model.Wishlist;
import com.example.keikoshop2.service.ICartService;
import com.example.keikoshop2.service.IProductService;
import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.IWishlistService;
import com.example.keikoshop2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    private UserService userService;
    private final IProductService productService;
    private final ICartService cartService;
    private final IWishlistService wishlistService;

    @Autowired
    public HomeController(UserService userService, IProductService productService, ICartService cartService, IWishlistService wishlistService) {
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
        this.wishlistService = wishlistService;
    }


    private boolean checkIfUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"));
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        int userId = user.getId();
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
        List<Product> products = productService.getAllProducts();
        model.addAttribute("cartItems", cartItems);
        boolean isAdmin = checkIfUserIsAdmin();
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("products", products);
        model.addAttribute("user", user);
        return "customer/home";
    }

    //masih sementara
    @GetMapping("/pesanan-saya")
    public String showPesananSaya(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        boolean isAdmin = checkIfUserIsAdmin();
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", user);
        return "customer/Pesanansaya";
        
    }

    @GetMapping("/product/detail/{id}")
    public String showProductDetail(@PathVariable int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        int userId = user.getId();
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
        boolean isAdmin = checkIfUserIsAdmin();
        boolean isInWishlist = wishlistService.existsByUserIdAndProductId(userId, id);
        Integer wishlistId = wishlistService.findWishlistIdByUserIdAndProductId(userId, id);

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", user);
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("isInWishlist", isInWishlist);
        model.addAttribute("wishlistId", wishlistId);
        return "customer/detailProduct";
    }



}
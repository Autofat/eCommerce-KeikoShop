package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.service.IProductService;
import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    private UserService userService;
    private final IProductService productService;

    @Autowired
    public HomeController(UserService userService, IProductService productService) {
        this.userService = userService;
        this.productService = productService;
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
        List<Product> products = productService.getAllProducts();
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
        model.addAttribute("user", user);
        return "customer/Pesanansaya";
        
    }
    


}
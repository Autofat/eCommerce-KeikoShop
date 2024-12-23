package com.example.keikoshop2.controller.customer;

import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.ICartService;
import com.example.keikoshop2.service.IProductService;
import com.example.keikoshop2.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.keikoshop2.model.Wishlist;
import com.example.keikoshop2.service.IWishlistService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final IWishlistService wishlistService;
    private final UserService userService;
    private final ICartService cartService;
    private final IProductService productService;

    private boolean checkIfUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"));
    }

    @PostMapping("/add-to-wishlist")
    public String storeWishlist(@RequestParam int userId, @RequestParam int productId, RedirectAttributes redirectAttributes) {
        if (wishlistService.existsByUserIdAndProductId(userId, productId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Item already in wishlist");
        } else {
            Wishlist wishlist = new Wishlist();
            wishlist.setUserId(userId);
            wishlist.setProductId(productId);
            wishlistService.store(wishlist);
            redirectAttributes.addFlashAttribute("successMessage", "Added to wishlist successfully");
        }
        return "redirect:/product/detail/" + productId;
    }

    @PostMapping("/delete/{id}")
    public String deleteWishlist(@PathVariable int id, RedirectAttributes redirectAttributes, @RequestParam int productId) {
        wishlistService.deleteWishlist(id);
        redirectAttributes.addFlashAttribute("successMessage", "Item removed from wishlist!");
        return "redirect:/product/detail/" + productId;
    }

    @GetMapping("/my")
    public String getAllWishlists(@RequestParam(required = false, defaultValue = "0")int productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        int userId = user.getId();
        List<Wishlist> wishlists = wishlistService.getWishlistsByUserId(userId);
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
        List<Product> products = wishlists.stream()
                .map(wishlist -> productService.getProductById(wishlist.getProductId()))
                .collect(Collectors.toList());
        boolean isAdmin = checkIfUserIsAdmin();
        boolean isInWishlist = wishlists.stream().anyMatch(wishlist -> wishlist.getProductId() == productId);
        model.addAttribute("products", products);
        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("products", products);
        model.addAttribute("isInWishlist", isInWishlist);
        return "customer/wishlist";
    }


    @PostMapping("/update/{id}")
    public String updateWishlist(@PathVariable int id, @RequestBody Wishlist updatedWishlist, RedirectAttributes redirectAttributes) {
        wishlistService.updateWishlist(id, updatedWishlist);
        redirectAttributes.addFlashAttribute("successMessage", "Wishlist item updated successfully!");
        return "redirect:/wishlist";
    }
}
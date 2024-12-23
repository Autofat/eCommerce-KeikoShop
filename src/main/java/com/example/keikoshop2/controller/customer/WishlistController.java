package com.example.keikoshop2.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.keikoshop2.model.Wishlist;
import com.example.keikoshop2.service.IWishlistService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    // private final IWishlistService wishlistService;
    // @PostMapping("/add-to-wishlist")
    // public String storeWishlist(Wishlist wishlist, RedirectAttributes
    // redirectAttributes) {
    // wishlistService.store(wishlist);
    // redirectAttributes.addFlashAttribute("successMessage", "Added to wishlist
    // successfully");
    // return "redirect:/products"; // Redirect back to products page or wherever
    // appropriate
    // }

    // @GetMapping
    // public String getAllWishlists(Model model) {
    // List<Wishlist> wishlists = wishlistService.getAllWishlists();
    // model.addAttribute("wishlists", wishlists);
    // return "wishlist"; // Mengarah ke template "wishlist.html"
    // }

    // // Menghapus item dari wishlist berdasarkan ID
    // @PostMapping("/delete/{id}")
    // public String deleteWishlist(int id, RedirectAttributes redirectAttributes) {
    // wishlistService.deleteWishlist(id);
    // redirectAttributes.addFlashAttribute("successMessage", "Item removed from
    // wishlist!");
    // return "redirect:/wishlist";
    // }

    // // Update item wishlist
    // @PostMapping("/update/{id}")
    // public String updateWishlist(
    // @PathVariable int id,
    // @RequestBody Wishlist updatedWishlist,
    // RedirectAttributes redirectAttributes
    // ) {
    // wishlistService.updateWishlist(id, updatedWishlist);
    // redirectAttributes.addFlashAttribute("successMessage", "Wishlist item updated
    // successfully!");
    // return "redirect:/wishlist";
    // }

}

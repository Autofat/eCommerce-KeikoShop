package com.example.keikoshop2.controller.admin;

import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")

public class ProductController {

    private final IProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.FOUND);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/manage-products")
    public String manageProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("newProduct", new Product());
        return "admin/products";
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, @RequestParam("imageFiles") MultipartFile image, RedirectAttributes redirectAttributes) {
        try {
            productService.createProduct(product, image);
            redirectAttributes.addFlashAttribute("successMessage", "Product successfully added");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/products/manage-products";
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute Product product, @RequestParam("imageFiles") MultipartFile image, RedirectAttributes redirectAttributes) {
        try {
            productService.updateProduct(product, id, image);
            redirectAttributes.addFlashAttribute("successMessage", "Product successfully updated");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/products/manage-products";
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product berhasil dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus product");
        }
        return "redirect:/admin/products/manage-products";
    }

    @GetMapping("/getProduct/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        logger.info("Number of products fetched: " + products.size());
        model.addAttribute("products", products);
        return "customer/home";
    }
}
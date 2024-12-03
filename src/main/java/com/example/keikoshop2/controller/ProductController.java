package com.example.keikoshop2.controller;

import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor

@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.FOUND);
    }

    @GetMapping("/manage-products")
    public String manageProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        model.addAttribute("newProduct", new Product());
        return "Admin/product.html";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            productService.createProduct(product);
            redirectAttributes.addFlashAttribute("successMessage", "Product berhasil ditambahkan");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan product");
        }
        return "redirect:/products/manage-products";
    }

    @PutMapping("/edit/{id}")
    @ResponseBody
    public Product updateProduct(@RequestBody Product product, @PathVariable int id) {
        return productService.updateProduct(product, id);
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product berhasil dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus product");
        }
        return "redirect:/products/manage-products";
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }


}

package com.example.keikoshop2.controller;

import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    @ResponseBody
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/edit/{id}")
    @ResponseBody
    public Product updateProduct(@RequestBody Product product, @PathVariable int id) {
        return productService.updateProduct(product, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/manage-products")
    public String manageProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product";
    }

}

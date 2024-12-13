package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();

    Product createProduct(Product product, MultipartFile image);

    Product updateProduct(Product product, int id, MultipartFile image);

    void deleteProduct(int id);

    Product getProductById(int id);

}

package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product updateProduct(Product product, int id);
    void deleteProduct(int id);



}

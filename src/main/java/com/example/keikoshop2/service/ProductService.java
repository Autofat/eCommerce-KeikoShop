package com.example.keikoshop2.service;

import com.example.keikoshop2.exception.ProductNotFoundExeption;
import com.example.keikoshop2.exception.StudentAlreadyExistsExeption;
import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        if (productAlreadyExists(product.getName())){
            throw new StudentAlreadyExistsExeption(product.getId() +"-"+ product.getName() + "Already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, int id) {
        return productRepository.findById(id).map(pr -> {
            pr.setName(product.getName());
            pr.setPrice(product.getPrice());
            pr.setDescription(product.getDescription());
            pr.setImage(product.getImage());
            pr.setStock(product.getStock());
            return productRepository.save(pr);
        }).orElseThrow(() -> new ProductNotFoundExeption("Product Not Found!"));
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExeption("Product Not Found With Id: " + id));
    }


    @Override
    public void deleteProduct(int id) {
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundExeption("Product Not Found!");
        }

    }

    private boolean productAlreadyExists(String name) {
        return productRepository.findByName(name).isPresent();
    }
}

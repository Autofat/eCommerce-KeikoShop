package com.example.keikoshop2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Product name is required")
    private String name;
    private String image;
    private String description;
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private int stock;

    // No-args constructor
    public Product() {}

    public Product(String name, String image, String description, double price, int stock) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
    //Getter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
//  Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}

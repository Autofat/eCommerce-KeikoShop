package com.example.keikoshop2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "wishlist_users") // Memberikan nama tabel
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "product_id", nullable = false, unique = true)
    private int productId;

    // Default Constructor (Wajib untuk JPA)
    public Wishlist() {
    }

    // Constructor dengan parameter
    public Wishlist(int id, int userId, int productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    // Getter dan Setter
    public int getId(){
        return this.id;
    }

    public int getUserId(){
        return this.userId;
    }

    public int getProductId(){
        return this.productId;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setProductId(int productId){
        this.productId = productId;
    }
}

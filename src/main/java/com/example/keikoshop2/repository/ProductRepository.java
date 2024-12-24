package com.example.keikoshop2.repository;

import com.example.keikoshop2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE UPPER(p.name) LIKE CONCAT('%', UPPER(:name), '%')")
    List<Product> findByNameContainingIgnoreCase(@Param("name") String name);
}

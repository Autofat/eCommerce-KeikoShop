package com.example.keikoshop2.repository;

import com.example.keikoshop2.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(int userId);

    List<Cart> findByUserIdAndIsDeletedFalse(int userId);

    Optional<Cart> findByUserIdAndProductId(int userId, int id);

    Optional<Cart> findByUserIdAndProductIdAndIsDeletedFalse(int userId, int id);

    List<Cart> findByProductId(int productId);
}
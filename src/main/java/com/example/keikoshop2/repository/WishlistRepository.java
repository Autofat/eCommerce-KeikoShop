package com.example.keikoshop2.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.keikoshop2.model.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    // Mengambil semua wishlist berdasarkan userId
    @Query("SELECT w FROM Wishlist w WHERE w.userId = :userId")
    List<Wishlist> findByUserId(@Param("userId") Integer userId);

    // Mengambil wishlist berdasarkan nama item (contoh fitur pencarian)
    @Query("SELECT w FROM Wishlist w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Wishlist> findByNameContainingIgnoreCase(@Param("name") String name);

    // Menghapus semua item wishlist berdasarkan userId
    void deleteByUserId(Integer userId);
}

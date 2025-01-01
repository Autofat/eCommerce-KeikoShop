package com.example.keikoshop2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.keikoshop2.model.Notifikasi;

public interface NotifikasiRepository extends JpaRepository<Notifikasi, Integer> {
    List<Notifikasi> findByUserId(int userId);
}

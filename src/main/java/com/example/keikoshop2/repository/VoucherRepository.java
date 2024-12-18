package com.example.keikoshop2.repository;

import com.example.keikoshop2.model.Voucher;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
  Optional<Voucher> findByCode(String code);
}

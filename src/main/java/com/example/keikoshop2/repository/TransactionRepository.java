package com.example.keikoshop2.repository;

import com.example.keikoshop2.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {
  boolean existsByuserId(int userId);

  boolean existsByOrderId(String orderId);

  List<Transactions> findByUserId(int userId);

  List<Transactions> findByIsPaid(boolean isPaid);

  Optional<Transactions> findByOrderId(String orderId);

}

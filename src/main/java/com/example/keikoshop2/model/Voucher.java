package com.example.keikoshop2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Voucher {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private int discount;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  private boolean isValid;

  // Default Constructor (Wajib untuk JPA)
  public Voucher() {
  }

  // Constructor dengan parameter
  public Voucher(String code, int discount) {
    this.code = code;
    this.discount = discount;
  }

  // Getter dan Setter
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public boolean getIsValid() {
    return isValid;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getDiscount() {
    return discount;
  }

  public void setDiscount(int discount) {
    this.discount = discount;
  }

  public void setIsValid(boolean isValid) {
    this.isValid = isValid;
  }
}

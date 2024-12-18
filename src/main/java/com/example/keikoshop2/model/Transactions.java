package com.example.keikoshop2.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "carts_parent")
public class Transactions {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private int userId;

  @Column(nullable = false)
  private double totalPrice;

  @Type(JsonType.class)
  @Column(columnDefinition = "json")
  private String cart_ids;

  @Column(nullable = false)
  private boolean isPaid = false;

  @Column
  private String midtransTransactionId;

  @Column
  private String address_line_1;

  @Column
  private String address_line_2;

  @Column
  private String city;

  @Column
  private String province;

  @Column
  private String postal_code;

  @Column
  private String country;

  public Transactions() {
  }

  public Transactions(int userId, double totalPrice, String cart_ids) {
    this.userId = userId;
    this.totalPrice = totalPrice;
    this.cart_ids = cart_ids;
  }

  public Transactions(int userId, double totalPrice, String cart_ids, String address_line_1, String address_line_2,
      String city, String province, String postal_code, String country) {
    this.userId = userId;
    this.totalPrice = totalPrice;
    this.cart_ids = cart_ids;
    this.address_line_1 = address_line_1;
    this.address_line_2 = address_line_2;
    this.city = city;
    this.province = province;
    this.postal_code = postal_code;
    this.country = country;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getCart_ids() {
    return cart_ids;
  }

  public void setCart_ids(String cart_ids) {
    this.cart_ids = cart_ids;
  }
}
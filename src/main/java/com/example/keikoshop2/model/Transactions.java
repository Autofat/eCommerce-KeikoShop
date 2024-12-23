package com.example.keikoshop2.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
public class Transactions {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private String orderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "transactions" })
  private User user;

  @Column(nullable = false)
  private int userId;

  @Column(nullable = false)
  private double totalPrice;

  @Type(JsonType.class)
  @Column(columnDefinition = "json")
  private String cart_ids;

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

  @Column(nullable = false)
  private Boolean isPaid = false;

  @Column
  private Boolean isCancelled = false;

  @Column
  private Boolean isConfirmed = false;

  @Column
  private Boolean usingPromo = false;

  @Column
  private int promoDiscount = 0;

  @Column(nullable = true)
  private String paymentProofImage;

  @Column
  private Boolean isRating = false;

  @Transient
  private List<Product> products;

  @Transient
  private List<Cart> carts;

  public Transactions() {
    this.orderId = "ORDER-" + UUID.randomUUID().toString();
  }

  public Transactions(int userId, double totalPrice, String cart_ids) {
    this();
    this.userId = userId;
    this.totalPrice = totalPrice;
    this.cart_ids = cart_ids;
  }

  public Transactions(int userId, double totalPrice, String cart_ids, String address_line_1, String address_line_2,
      String city, String province, String postal_code, String country) {
    this();
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

  public String getAddress_line_1() {
    return address_line_1;
  }

  public void setAddress_line_1(String address_line_1) {
    this.address_line_1 = address_line_1;
  }

  public String getAddress_line_2() {
    return address_line_2;
  }

  public void setAddress_line_2(String address_line_2) {
    this.address_line_2 = address_line_2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getPostal_code() {
    return postal_code;
  }

  public void setPostal_code(String postal_code) {
    this.postal_code = postal_code;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Boolean IsPaid() {
    return isPaid;
  }

  public void setPaid(Boolean paid) {
    isPaid = paid;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Boolean isCancelled() {
    return isCancelled;
  }

  public void setCancelled(Boolean cancelled) {
    isCancelled = cancelled;
  }

  public Boolean isConfirmed() {
    return isConfirmed;
  }

  public void setConfirmed(Boolean confirmed) {
    isConfirmed = confirmed;
  }

  public Boolean isUsingPromo() {
    return usingPromo;
  }

  public void setUsingPromo(Boolean usingPromo) {
    this.usingPromo = usingPromo;
  }

  public int getPromoDiscount() {
    return promoDiscount;
  }

  public void setPromoDiscount(int promoDiscount) {
    this.promoDiscount = promoDiscount;
  }

  public String getPaymentProofImage() {
    return paymentProofImage;
  }

  public void setPaymentProofImage(String paymentProofImage) {
    this.paymentProofImage = paymentProofImage;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProduct(List<Product> products) {
    this.products = products;
  }

  public List<Integer> getCartIds() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(cart_ids, new TypeReference<List<Integer>>() {
      });
    } catch (Exception e) {
      return List.of();
    }
  }

  public void setCartList(List<Cart> carts) {
    this.carts = carts;
  }

  public Boolean isRating() {
    return isRating;
  }

  public void setIsRating(Boolean rating) {
    isRating = rating;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
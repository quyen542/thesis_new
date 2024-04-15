package com.example.thesis_new.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "food_rating")
public class FoodRating {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private double quality;

    private double price;

    private double packaged;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private User customer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    @JsonBackReference
    private Food foodRating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPackaged() {
        return packaged;
    }

    public void setPackaged(double packaged) {
        this.packaged = packaged;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Food getFoodRating() {
        return foodRating;
    }

    public void setFoodRating(Food foodRating) {
        this.foodRating = foodRating;
    }
}

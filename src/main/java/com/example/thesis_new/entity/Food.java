package com.example.thesis_new.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Enter food name")
    @Column(nullable = false, unique = true, length = 45)
    private String name;

    @NotBlank(message = "Enter food price")
    @Column(nullable = false, length = 20)
    private String price;

    @NotBlank(message = "Enter food category")
    @Column(nullable = false, length = 20)
    private String category;

    @Column(nullable = true, length = 250)
    private String description;

    @Column(nullable = true)
    private double rating;

    @Column(nullable = true, length = 64)
    private String photos;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @ManyToMany(mappedBy = "likedFood")
    private Collection<User> likes;

    @ManyToMany(mappedBy = "dislikedFood")
    private Collection<User> dislikes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/food-photos/" + id + "/" + photos;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Collection<User> getLikes() {
        return likes;
    }

    public void setLikes(Collection<User> likes) {
        this.likes = likes;
    }

    public Collection<User> getDislikes() {
        return dislikes;
    }

    public void setDislikes(Collection<User> dislikes) {
        this.dislikes = dislikes;
    }
}

package com.example.thesis_new.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @NotBlank(message = "Enter your Username")
    @Column(nullable = false, unique = true, length = 45)
    private String username;

    @Column(unique = true, length = 45)
    private String email;

    @NotBlank(message = "Enter your name")
    @Column(nullable = false, length = 20)
    private String name;

    @NotBlank(message = "Enter your password")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Re-enter your password")
    @Column(nullable = false)
    private String Repassword;

    @Column(length = 20)
    private String Phonenumber = "";

    @Column(length = 200)
    private String address = "";

    private String lat;

    private String lon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Cart cart;

    @OneToMany(fetch = FetchType.EAGER,  mappedBy = "user")
    private Collection<Order> orders = new ArrayList<Order>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deliveryPerson")
    private Collection<Order> shipmentOrders = new ArrayList<Order>();

    @OneToOne(mappedBy = "deliveryPerson", cascade = CascadeType.ALL)
    @JsonManagedReference
    private DeliveryInfo deliveryInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return Repassword;
    }

    public void setRepassword(String repassword) {
        Repassword = repassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Collection<Order> getShipmentOrders() {
        return shipmentOrders;
    }

    public void setShipmentOrders(Collection<Order> shipmentOrders) {
        this.shipmentOrders = shipmentOrders;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", Repassword='" + Repassword + '\'' +
                ", Phonenumber='" + Phonenumber + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", cart=" + cart +
                ", orders=" + orders +
                '}';
    }
}

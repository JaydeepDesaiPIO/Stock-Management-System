package com.spring.stockmanagement.entities;

import javax.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    private long quantity;

    private double price;

    private double totalPrice;

    //constructor

    public OrderItem() {
    }

    public OrderItem(Product product, Orders orders, long quantity, double price, double totalPrice) {
        this.product = product;
        this.orders = orders;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }
//getter setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

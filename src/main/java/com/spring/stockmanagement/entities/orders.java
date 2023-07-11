package com.spring.stockmanagement.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String orderNumber;

    private Date orderDate;

    private String status;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems;

    //constructor

    public orders() {
    }

    public orders(String orderNumber, Date orderDate, String status, User user, List<OrderItem> orderItems) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.status = status;
        this.user = user;
        this.orderItems = orderItems;
    }
//getter setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

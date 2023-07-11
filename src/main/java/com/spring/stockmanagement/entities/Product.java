package com.spring.stockmanagement.entities;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;

    private int productPrice;

    private long quantity;

    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    //constructor

    public Product() {
    }

    public Product(String productName, int productPrice, long quantity, String description, Company company) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.description = description;
        this.company = company;
    }
//getter setter

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

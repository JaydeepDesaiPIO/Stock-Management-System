package com.spring.stockmanagement.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "company",
        uniqueConstraints =
        @UniqueConstraint(name = "name_unique", columnNames = "company_name")
)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int id;

    @Column(name = "company_name")
    private String name;

    private String address;

    private long contactNo;

    @OneToMany(mappedBy = "company")
    private List<User> user;

    @OneToMany(mappedBy = "company")
    private List<Product> product;

    //constructor
    public Company() {
    }

    public Company(String name, String address, long contactNo, List<User> user, List<Product> product) {
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
        this.user = user;
        this.product = product;
    }

    //getter setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getContactNo() {
        return contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }


}

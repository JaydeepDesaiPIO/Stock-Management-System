package com.spring.stockmanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "company",
        uniqueConstraints =
        @UniqueConstraint(name = "name_unique", columnNames = "company_name")
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int id;

    @Column(name = "company_name")
    private String companyName;

    private String companyAddress;

    private String contactNo;

    @OneToMany(mappedBy = "company")
    private List<User> user;

    @OneToMany(mappedBy = "company")
    private List<Product> product;

    //constructor
//    public Company() {
//    }
//
//    public Company(String name, String address, long contactNo, List<User> user, List<Product> product) {
//        this.name = name;
//        this.address = address;
//        this.contactNo = contactNo;
//        this.user = user;
//        this.product = product;
//    }

    //getter setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
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

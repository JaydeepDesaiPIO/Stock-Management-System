package com.spring.stockmanagement.entities;

import com.spring.stockmanagement.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name")
    @Size(min = 4, message = "Atleast 4 characters")
    private String name;

    private String email;

    private String password;

    private String contact;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyCart> myCart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Orders> orders;

    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contact=" + contact +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                ", company=" + company +
                '}';
    }

    //constructor
//    public User() {
//    }
//
//    public User(String name, String email, String password, Long contact, String role, String address, Company company) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.contact = contact;
//        this.role = role;
//        this.address = address;
//        this.company = company;
//    }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<MyCart> getMyCart() {
        return myCart;
    }

    public void setMyCart(List<MyCart> myCart) {
        this.myCart = myCart;
    }
}

package com.spring.stockmanagement.entities;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.spring.stockmanagement.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;
import org.springframework.jca.cci.object.SimpleRecordOperation;

import javax.persistence.*;
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
    private String name;

    private String email;

    private String password;

    private Long contact;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

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

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
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
}

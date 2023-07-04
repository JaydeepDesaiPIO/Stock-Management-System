package com.spring.stockmanagement.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "stock_name")
    private String stock;

    private String symbol;

    private String type;

    @Column(name = "stock_price")
    private double price;

    private Date listedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

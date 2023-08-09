package com.spring.stockmanagement.entities;

import com.spring.stockmanagement.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_gen")
    @GenericGenerator(name = "order_gen", strategy = "com.spring.stockmanagement.entities.SequenceGenerator", parameters = {
            @Parameter(name = SequenceGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = SequenceGenerator.VALUE_PREFIX_PARAMETER, value = "ORD"),
            @Parameter(name = SequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
    })
    private String id;
    private String orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

//getter setter

    @Override
    public String toString() {
        return "Orders{" +
                "id='" + id + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", status=" + status +
                ", user=" + user +
                ", orderItems=" + orderItems +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

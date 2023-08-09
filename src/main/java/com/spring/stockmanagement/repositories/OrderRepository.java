package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.Enum.OrderStatus;
import com.spring.stockmanagement.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    public List<Orders> getOrdersByUserId(int id);

    public List<Orders> getOrderByStatus(OrderStatus status);
}

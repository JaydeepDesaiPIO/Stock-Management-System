package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    public List<Orders> getOrdersByUserId(int id);
}

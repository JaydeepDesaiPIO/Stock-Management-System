package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.Enum.OrderStatus;
import com.spring.stockmanagement.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void saveOrder()
    {


        Orders o= Orders.builder()
                .status(OrderStatus.PENDING)

               // .orderItems()
                .build();
        orderRepository.save(o);
        OrderItem ot=OrderItem.builder()
              //  .product(new Product("pencil",10,100,"good pen",new Company()))
                .price(10)
                .quantity(10)
                .totalPrice(100)
                .orders(o)
                .build();
        orderItemRepository.save(ot);
    }
}
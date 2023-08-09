package com.spring.stockmanagement.service;

import com.spring.stockmanagement.Enum.OrderStatus;
import com.spring.stockmanagement.entities.Orders;
import com.spring.stockmanagement.repositories.OrderRepository;
import com.spring.stockmanagement.service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerTask {

    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(cron = "0 */12 * * * *")
    public void setOrderStatus()
    {
        List<Orders> listOfOrderedHistory=orderRepository.getOrderByStatus(OrderStatus.SHIPPED);
        for (Orders orders: listOfOrderedHistory)
        {
            orders.setStatus(OrderStatus.DELIVERED);
            orderRepository.save(orders);
        }
    }
}

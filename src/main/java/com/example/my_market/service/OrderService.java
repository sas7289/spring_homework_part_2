package com.example.my_market.service;

import com.example.my_market.entity.Order;
import com.example.my_market.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public UUID save(UUID userId) {
        Order order = orderRepository.save(new Order(userId));
        return order.getId();
    }
}
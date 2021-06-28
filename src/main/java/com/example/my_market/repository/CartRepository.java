package com.example.my_market.repository;

import com.example.my_market.entity.Cart;
import com.example.my_market.entity.Product;
import com.example.my_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    List<Cart> findAllByUserId(UUID id);

    List<Cart> findByUserLogin(String login);

    Cart findByUserAndProduct(User userId, Product productId);

    @Query(value = "update cart set order_id = ?2 where user_id = ?1", nativeQuery = true)
    void updateOrderId(UUID userId, UUID orderId);
}

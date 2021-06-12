package com.example.my_market.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "poroduct_id")
    private UUID product_id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "user_id")
    private UUID user_id;
}

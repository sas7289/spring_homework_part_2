package com.example.my_market.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "text")
    private String text;

    @Column(name = "product_id")
    private UUID product_id;
}

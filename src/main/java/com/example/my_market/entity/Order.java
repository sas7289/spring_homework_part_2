package com.example.my_market.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class Order {
    @Id
    private UUID id;

    @PrePersist
    private void serId(){
        id = UUID.randomUUID();
    }

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "date_of_creation")
    private Timestamp date_of_creation;

    public Order() {
    }

    public Order(UUID userId) {
        this.userId = userId;
        date_of_creation = new Timestamp(System.currentTimeMillis());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Timestamp getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(Timestamp date_of_creation) {
        this.date_of_creation = date_of_creation;
    }
}

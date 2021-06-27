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

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_of_creation")
    private Timestamp date_of_creation;

    public Order() {
    }

    public Order(User user) {
        this.user = user;
        date_of_creation = new Timestamp(System.currentTimeMillis());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(Timestamp date_of_creation) {
        this.date_of_creation = date_of_creation;
    }
}

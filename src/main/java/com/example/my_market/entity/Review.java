package com.example.my_market.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "review")
public class Review {
    @Id
    private UUID id;

    @PrePersist
    private void setId(){
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

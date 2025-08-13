package com.expenseservice.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
public class Expense {
    @Id
    private String id;

    private String userId;

    private Long amount;

    public Expense(String id, String userId, Long amount, String merchant) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.merchant = merchant;
    }

    private String merchant;

    @CreationTimestamp
    @Column(name = "transaction_time",nullable = false,updatable = false)
    private LocalDateTime transactionAt;

    public Expense() {
    }

    public Expense(String userId, Long amount, String merchant) {
        this.userId = userId;
        this.amount = amount;
        this.merchant = merchant;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
}

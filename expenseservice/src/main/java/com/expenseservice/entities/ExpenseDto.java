package com.expenseservice.entities;

import jakarta.persistence.Id;

public class ExpenseDto {
    public ExpenseDto(String userId, Long amount, String merchant) {
        this.userId = userId;
        this.amount = amount;
        this.merchant = merchant;
    }

    public ExpenseDto() {
    }
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExpenseDto(String id, String userId, Long amount, String merchant) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.merchant = merchant;
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

    @Id
    private String userId;

    private Long amount;

    private String merchant;

}

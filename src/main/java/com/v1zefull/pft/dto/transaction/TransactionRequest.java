package com.v1zefull.pft.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {
    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    private String description;

    @NotNull
    @Positive
    private Long categoryId;

    @NotNull
    @Positive
    private Long userId;

    public TransactionRequest(){
    }

    public TransactionRequest(BigDecimal amount, LocalDate date, String description, Long categoryId, Long userId) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
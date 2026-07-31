package com.v1zefull.pft.dto.transaction;

import com.v1zefull.pft.dto.category.CategoryResponse;
import com.v1zefull.pft.dto.user.UserResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {
    private final Long id;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;
    private final CategoryResponse category;
    private final UserResponse user;


    public TransactionResponse(
            Long id,
            BigDecimal amount,
            LocalDate date,
            String description,
            CategoryResponse category,
            UserResponse user
    ) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public UserResponse getUser() {
        return user;
    }
}

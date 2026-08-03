package com.v1zefull.pft.dto.category;

import com.v1zefull.pft.entity.enums.TransactionType;

public class CategoryResponse {
    private final Long id;
    private final String name;
    private final TransactionType type;

    public CategoryResponse(Long id, String name, TransactionType type){
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TransactionType getType() {
        return type;
    }
}

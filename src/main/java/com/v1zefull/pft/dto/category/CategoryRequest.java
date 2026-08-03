package com.v1zefull.pft.dto.category;

import com.v1zefull.pft.entity.enums.TransactionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryRequest {
    @NotBlank
    private String name;

    @NotNull
    private TransactionType type;

    public CategoryRequest(){
    }

    public CategoryRequest(String name, TransactionType type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public TransactionType getType(){
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}

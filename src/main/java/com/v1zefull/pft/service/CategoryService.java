package com.v1zefull.pft.service;

import com.v1zefull.pft.entity.Category;
import com.v1zefull.pft.exception.ResourceNotFoundException;
import com.v1zefull.pft.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found with id " + id)
        );
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id){
        Category category = getCategoryById(id);
        categoryRepository.deleteById(category.getId());
    }
}

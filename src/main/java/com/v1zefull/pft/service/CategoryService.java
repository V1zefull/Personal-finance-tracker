package com.v1zefull.pft.service;

import com.v1zefull.pft.dto.category.CategoryRequest;
import com.v1zefull.pft.dto.category.CategoryResponse;
import com.v1zefull.pft.entity.Category;
import com.v1zefull.pft.exception.ResourceNotFoundException;
import com.v1zefull.pft.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //Entity -> Response
    public CategoryResponse toResponse(Category category){
        return new CategoryResponse(category.getId(), category.getName(), category.getType());
    }

    //Request -> Entity
    private Category toEntity(CategoryRequest request){
        return new Category(request.getName(), request.getType());
    }

    public CategoryResponse createCategory(CategoryRequest request){
        Category category = toEntity(request);
        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    public CategoryResponse getCategoryById(Long id){
        Category category = getCategoryEntityById(id);
        return toResponse(category);
    }

    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = getCategoryEntityById(id);
        category.setName(request.getName());
        category.setType(request.getType());

        Category updated = categoryRepository.save(category);
        return toResponse(updated);
    }

    public void deleteCategory(Long id){
        Category category = getCategoryEntityById(id);
        categoryRepository.deleteById(category.getId());
    }
}

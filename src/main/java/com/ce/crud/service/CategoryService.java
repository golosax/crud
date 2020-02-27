package com.ce.crud.service;

import com.ce.crud.entity.Category;

import java.util.Optional;

public interface CategoryService {

    Iterable<Category> getCategories();

    Optional<Category> getCategoryById(Integer id);

    Category saveCategory(Category category);

    void deleteCategory(Integer id);
}

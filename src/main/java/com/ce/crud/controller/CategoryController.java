package com.ce.crud.controller;


import com.ce.crud.entity.Category;
import com.ce.crud.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/categories")
    public Iterable<Category> getCategories() {
        log.info("Request to get all categories");
        return categoryService.getCategories();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") Integer id) {
        log.info(String.format("Request to get category with id {%s}", id));
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Category> deleteById(@PathVariable("id") Integer id) {
        log.info(String.format("Request to delete category with id: {%s}", id));
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}

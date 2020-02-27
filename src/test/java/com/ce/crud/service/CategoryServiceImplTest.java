package com.ce.crud.service;

import com.ce.crud.entity.Category;
import com.ce.crud.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryServiceImplTest {

    @MockBean
    CategoryRepository categoryRepository;

    CategoryService categoryService;
    Category testCategory;

    public static final int PRODUCT_ID = 1;

    @BeforeEach
    public void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);

        testCategory = new Category();
        testCategory.setId(1);
        testCategory.setName("clothes");
    }

    @Test
    public void getCategories() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(testCategory));

        assertEquals(categoryService.getCategories(), Collections.singletonList(testCategory));
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void getCategoryById() {
        when(categoryRepository.findById(any())).thenReturn(Optional.of(testCategory));

        assertEquals(categoryService.getCategoryById(PRODUCT_ID), Optional.of(testCategory));
        verify(categoryRepository, times(1)).findById(anyInt());
    }

    @Test
    public void saveCategory() {
        when(categoryRepository.save(testCategory)).thenReturn(testCategory);

        assertEquals(categoryService.saveCategory(testCategory), testCategory);
        verify(categoryRepository, times(1)).save(any());
    }
}
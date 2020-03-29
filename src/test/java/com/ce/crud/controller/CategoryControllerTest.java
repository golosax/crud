package com.ce.crud.controller;

import com.ce.crud.entity.Category;
import com.ce.crud.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Test
    void getCategories() throws Exception {

        // given
        Category category = new Category(1, "cat1");
        List<Category> list = new ArrayList() {{
            add(category);
        }};

        // when
        when(categoryController.getCategories()).thenReturn(list);

        // test
        mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(category.getId())))
                .andExpect(jsonPath("$[0].name", is(category.getName())));

        verify(categoryService, times(1)).getCategories();
    }

    @Test
    void getById_CategoryExists() throws Exception {
        // given
        Category category = new Category(1, "cat1");

        // when
        when(categoryService.getCategoryById(1)).thenReturn(Optional.of(category));

        // test
        mockMvc.perform(get("/category/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(category.getId())))
                .andExpect(jsonPath("name", is(category.getName())));

        verify(categoryService, times(1)).getCategoryById(1);
    }

    @Test
    void getById_CategoryNotFound() throws Exception {
        // when
        when(categoryService.getCategoryById(1)).thenReturn(Optional.empty());

        // test
        mockMvc.perform(get("/category/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryById(1);
    }

    @Test
    void deleteById() throws Exception {
        // test
        mockMvc.perform(delete("/category/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(1);
    }

}
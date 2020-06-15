package com.ce.crud.service;

import com.ce.crud.entity.Product;
import com.ce.crud.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceImplTest {

    @MockBean
    ProductRepository productRepository;

    ProductService productService;
    Product testProduct;

    public static final int PRODUCT_ID = 1;

    @BeforeEach
    public void setUp() {
        productService = new ProductServiceImpl(productRepository);

        testProduct = new Product();
        testProduct.setId(PRODUCT_ID);
        testProduct.setName("jeans");
        testProduct.setPrice(new BigDecimal(50));
    }

    @Test
    public void listAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(testProduct));

        assertEquals(productService.getAllProducts(), Collections.singletonList(testProduct));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProductById() {
        when(productRepository.findById(any())).thenReturn(Optional.of(testProduct));

        assertEquals(productService.getProductById(PRODUCT_ID), Optional.of(testProduct));
        verify(productRepository, times(1)).findById(anyInt());
    }

    @Test
    public void saveProduct() {
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        assertEquals(productService.saveProduct(testProduct), testProduct);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void deleteProduct() {
        // when
        when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);

        // test
        productService.deleteProduct(PRODUCT_ID);
        verify(productRepository, times(1)).existsById(PRODUCT_ID);
        verify(productRepository, times(1)).deleteById(PRODUCT_ID);
    }

    @Test
    public void deleteProduct_idNotExist() {
        // when
        when(productRepository.existsById(any())).thenReturn(false);

        // test
        productService.deleteProduct(PRODUCT_ID);
        verify(productRepository, times(1)).existsById(PRODUCT_ID);
        verify(productRepository, times(0)).deleteById(anyInt());
    }
}

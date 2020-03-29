package com.ce.crud.controller;

import com.ce.crud.entity.Product;
import com.ce.crud.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private static final int PRODUCT_ID = 1;
    private static final String PRODUCT_NAME = "product_1";

    @Test
    void getListOfProducts() throws Exception {
        // given
        Product product = createDummyProduct();

        // when
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));

        // test
        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(product.getId())))
                .andExpect(jsonPath("$[0].name", is(product.getName())));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void createProduct() throws Exception {
        // given
        Product product = createDummyProduct();

        // when
        when(productService.saveProduct(product)).thenReturn(product);

        // test
        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(objectToJson(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(product.getId())))
                .andExpect(jsonPath("name", is(product.getName())));

        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    void getById_productExists() throws Exception {
        // given
        Product product = createDummyProduct();

        // when
        when(productService.getProductById(product.getId())).thenReturn(Optional.of(product));

        // test
        mockMvc.perform(get("/product/{id}", product.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(product.getId())))
                .andExpect(jsonPath("name", is(product.getName())));

        verify(productService, times(1)).getProductById(product.getId());
    }

    @Test
    void getById_productNotFound() throws Exception {
        // given
        Product product = createDummyProduct();

        // when
        when(productService.getProductById(product.getId())).thenReturn(Optional.empty());

        // test
        mockMvc.perform(get("/product/{id}", product.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist()); // no return body

        verify(productService, times(1)).getProductById(product.getId());
    }


    @Test
    void edit() throws Exception {
        // given
        Product product = createDummyProduct();

        // when
        when(productService.saveProduct(product)).thenReturn(product);

        // test
        mockMvc.perform(put("/product/{id}", product.getId()).contentType(MediaType.APPLICATION_JSON).content(objectToJson(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(product.getId())))
                .andExpect(jsonPath("name", is(product.getName())));

        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/product/{id}", PRODUCT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist()); // no return body

        verify(productService, times(1)).deleteProduct(PRODUCT_ID);
    }

    private String objectToJson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(o);
        return json;
    }

    private Product createDummyProduct() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);
        return product;
    }

}
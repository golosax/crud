package com.ce.crud.controller;

import com.ce.crud.entity.Product;
import com.ce.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/products")
    public Iterable<Product> getListOfProducts() {
        log.info("Request to get all products");
        return productService.getAllProducts();
    }

    @PostMapping(value = "product")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        log.info(String.format("Request to create product: {%s}", product));
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok().body(savedProduct);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        log.info(String.format("Request to get product with id {%s}", id));
        Optional<Product> product = productService.getProductById(id);
        return product.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("product/{id}")
    public ResponseEntity<Product> edit(@Valid @RequestBody Product product) {
        log.info(String.format("Request to edit product {%s}", product));
        Product editedProduct = productService.saveProduct(product);
        return ResponseEntity.ok().body(editedProduct);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id) {
        log.info(String.format("Request to delete product with id: {%s}", id));
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}

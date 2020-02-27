package com.ce.crud.service;

import com.ce.crud.entity.Product;

import java.util.Optional;

public interface ProductService {

    Iterable<Product> getAllProducts();

    Optional<Product> getProductById(Integer id);

    Product saveProduct(Product product);

    void deleteProduct(Integer id);

}

package com.ce.crud.tools;

import com.ce.crud.repository.CategoryRepository;
import com.ce.crud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Alternative way of DB initialization.
 * <p>
 * Uncomment CommandLineRunner interface and "run" method to make CRUD operations on repositories.
 */
@Component
@RequiredArgsConstructor
public class RepositoryInitializer { //implements CommandLineRunner {

    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;

//    @Override
//    public void run(String... args) throws Exception {
//        Category category = new Category(1, "N/A");
//
//        Product product = new Product();
//        product.setName("product1");
//        product.setPrice(BigDecimal.valueOf(100));
//        product.setCategory(category);
//
//        categoryRepository.save(category);
//        productRepository.save(product);
//    }
}

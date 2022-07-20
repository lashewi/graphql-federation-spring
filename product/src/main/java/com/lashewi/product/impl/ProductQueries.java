package com.lashewi.product.impl;


import com.lashewi.product.model.Product;
import com.lashewi.product.repository.ProductRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductQueries implements GraphQLQueryResolver {

    @NonNull
    private final ProductRepository productRepository;

    public List<Product> products() {
        log.info("Loading all the products");
        return productRepository.findAll();
    }

    public Product prodById(Long id) {
        log.info("Loading prod by id {}", id);
        return productRepository.findById(id).get();
    }

    public Product lookupProduct(@NotNull Long id) {
        log.info("lookupProduct product with ID: {}", id);
        return productRepository.findById(id).get();
    }

}
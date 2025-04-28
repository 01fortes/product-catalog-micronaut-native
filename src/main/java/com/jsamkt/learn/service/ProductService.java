package com.jsamkt.learn.service;

import com.jsamkt.learn.model.Product;
import com.jsamkt.learn.repository.ProductRepository;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> findById(Long id) {
        return productRepository.findProductNameById(id);
    }

    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }
}

package com.jsamkt.learn.controller;

import com.jsamkt.learn.model.Product;
import com.jsamkt.learn.service.ProductService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.tracing.annotation.ContinueSpan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/products/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Get("/")
    @ContinueSpan
    public Flux<Product> findAll() {
        return productService.findAll();
    }

    @Get("/{id}")
    @ContinueSpan
    public Mono<Product> findById(Long id) {
        return productService.findById(id);
    }

    @Post("/")
    @ContinueSpan
    public Mono<Product> save(
            @Body Product product) {
        return productService.save(product);
    }

}
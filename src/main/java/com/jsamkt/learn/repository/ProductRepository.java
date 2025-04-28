package com.jsamkt.learn.repository;

import com.jsamkt.learn.model.Product;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Tuple;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class ProductRepository {

    private final Pool pool;

    public ProductRepository(@Named("vertx-pool") Pool pool) {
        this.pool = pool;
    }

    public Mono<Product> findProductNameById(Long id) {
        return Mono.create(sink -> {
            if (id == null) {
                sink.success(null);
                return;
            }
            if (id <= 0) {
                sink.error(new IllegalArgumentException("ID must be greater than 0"));
                return;
            }
            pool.preparedQuery(SQL_GET_ONE)
                    .execute(Tuple.of(id))
                    .onSuccess(rows -> {
                        var iterator = rows.iterator();
                        if (iterator.hasNext()) {
                            var row = iterator.next();
                            sink.success(
                                    new Product(
                                            row.getLong("id"),
                                            row.getString("name"),
                                            row.getString("description"),
                                            row.getBigDecimal("price"),
                                            row.getString("category"),
                                            row.getInteger("stock")
                                    )
                            );
                        } else {
                            sink.success(null);
                        }
                    })
                    .onFailure(sink::error);
        });
    }

    public Flux<Product> findAll() {
        return Flux.create(sink -> {
            pool.preparedQuery(SQL_GET_ALL)
                    .execute()
                    .onSuccess(rows -> {
                        rows.forEach(row -> {
                            sink.next(
                                    new Product(
                                            row.getLong("id"),
                                            row.getString("name"),
                                            row.getString("description"),
                                            row.getBigDecimal("price"),
                                            row.getString("category"),
                                            row.getInteger("stock")
                                    )
                            );
                        });
                        sink.complete();
                    })
                    .onFailure(sink::error);
        });
    }

    public Mono<Product> save(Product product) {
        return Mono.create(sink -> {
            if (product == null) {
                sink.error(new IllegalArgumentException("Product cannot be null"));
                return;
            }
            pool.preparedQuery(SQL_INSERT)
                    .execute(Tuple.of(
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getCategory(),
                            product.getStock()
                    ))
                    .onSuccess(rows -> {
                        var row = rows.iterator().next();
                        sink.success(
                                new Product(
                                        row.getLong("id"),
                                        product.getName(),
                                        product.getDescription(),
                                        product.getPrice(),
                                        product.getCategory(),
                                        product.getStock()
                                )
                        );
                    })
                    .onFailure(sink::error);
        });
    }

    private static final String SQL_GET_ONE = "SELECT * FROM products WHERE id = $1";
    private static final String SQL_GET_ALL = "SELECT * FROM products";
    private static final String SQL_INSERT = "INSERT INTO products (name, description, price, category, stock) VALUES ($1, $2, $3, $4, $5) RETURNING *";
}
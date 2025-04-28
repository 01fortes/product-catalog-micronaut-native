package com.jsamkt.learn.factory;

import com.jsamkt.learn.configuration.DatabaseProperties;
import io.micronaut.context.annotation.Factory;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class DatabaseFactory {

    @Singleton
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Singleton
    @Named("vertx-pool")
    public Pool createDatabasePool(
            Vertx vertx,
            DatabaseProperties dbProperties) {
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setHost(dbProperties.getHost())
                .setPort(dbProperties.getPort())
                .setDatabase(dbProperties.getDatabase())
                .setUser(dbProperties.getUsername())
                .setPassword(dbProperties.getPassword());

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(dbProperties.getMaxPoolSize());

        return Pool.pool(vertx, connectOptions, poolOptions);
    }
}

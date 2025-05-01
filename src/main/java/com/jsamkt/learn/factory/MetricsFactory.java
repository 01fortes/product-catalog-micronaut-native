package com.jsamkt.learn.factory;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.registry.otlp.OtlpConfig;
import io.micrometer.registry.otlp.OtlpMeterRegistry;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class MetricsFactory {

    @Singleton
    public MeterRegistry meterRegistry(OtlpConfig otlpConfig) {
        var result = new OtlpMeterRegistry(otlpConfig, Clock.SYSTEM);
        result.config().namingConvention(NamingConvention.snakeCase);
        return result;
    }
}

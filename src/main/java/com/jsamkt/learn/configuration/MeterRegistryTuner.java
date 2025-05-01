package com.jsamkt.learn.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

@Singleton
public class MeterRegistryTuner implements BeanCreatedEventListener<MeterRegistry> {
    @Override
    public MeterRegistry onCreated(@NonNull BeanCreatedEvent<MeterRegistry> event) {
        var bean = event.getBean();
        bean.config().namingConvention(NamingConvention.snakeCase);
        return bean;
    }
}

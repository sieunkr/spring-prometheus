package com.example.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CoffeeService {

    private final MeterRegistry meterRegistry;
    private List<Order> orders = new ArrayList<>();

    private final Counter americanoOrderCounter;
    private final Counter latteOrderCounter;


    public CoffeeService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("coffee.ordersInQueue", orders, Collection::size)
                .description("Number of unserved orders")
                .register(meterRegistry);

        americanoOrderCounter = Counter.builder("coffee.orders")
                .tag("type", "americano")
                .description("The number of orders ever placed for Americano coffees")
                .register(meterRegistry);

        latteOrderCounter = Counter.builder("coffee.orders")
                .tag("type", "latte")
                .description("The number of orders ever placed for Latte coffees")
                .register(meterRegistry);
    }

    @PostConstruct
    void init() {

        orders.add(new Order(1L));
        orders.add(new Order(2L));

        americanoOrderCounter.increment();
        latteOrderCounter.increment();
        latteOrderCounter.increment();

    }
}
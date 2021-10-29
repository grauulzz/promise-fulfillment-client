package com.amazon.ata.deliveringonourpromise.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    @Test
    void toStringTest() {

        Order order = Order.builder().withOrderId("123").build();
        assertTrue(order.toString().contains(order.getOrderId()));

    }
}

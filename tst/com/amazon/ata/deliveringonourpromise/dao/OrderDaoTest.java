package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.OrderItem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoTest {


    private final OrderManipulationAuthorityClient omaClient = App.getOrderManipulationAuthorityClient();
    OrderDao dao = new OrderDao(omaClient);

    @Test
    public void get_nonexistentOrder_returnsEmptyList() {
        // GIVEN - invalid/nonexistent order item ID


        // WHEN
        Order order = dao.get("123");

        // THEN
        assertNull(order);
    }

    @Test
    public void checkAddOrderToOrderItemList_returnsTrue() {
        // GIVEN - A mock order ID from build


        // WHEN
        Order order = dao.get("900-3746401-0000002");
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(order.getCustomerOrderItemList().get(0));

        OrderItem getBuilderItem = OrderItem.builder().withOrderId("900-3746401-0000002").build();


        // THEN
        assertEquals(orderItemList.get(0).getOrderId(), getBuilderItem.getOrderId());
    }

}

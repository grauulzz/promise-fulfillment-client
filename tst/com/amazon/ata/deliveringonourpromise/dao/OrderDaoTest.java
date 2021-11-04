package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.deliveringonourpromise.types.OrderItem;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.order.OrderFieldValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoTest {


    private final OrderManipulationAuthorityClient omaClient = App.getOrderManipulationAuthorityClient();
    OrderDao dao = new OrderDao(omaClient);


    @Test
    public void givenNullString_whenCheckNotNullWithMessage_throwsException () {

        OrderFieldValidator order = new OrderFieldValidator();

        boolean condition = order.isValidOrderId(null);

        assertFalse(condition);

    }

    @Test
    public void get_nonexistentOrderItemId_returnsEmptyList() {
        // GIVEN - invalid/nonexistent order item ID


        // WHEN
        Order promises = dao.get("123");

        // THEN
        assertNull(promises);
    }

    @Test
    public void get_existentOrderItemId_returnsEmptyList() {
        // GIVEN - A mock order ID from build


        // WHEN
        Order promises = dao.get("900-3746401-0000002");
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(promises.getCustomerOrderItemList().get(0));

        OrderItem getBuilderItem = OrderItem.builder().withOrderId("900-3746401-0000002").build();


        // THEN
        assertEquals(orderItemList.get(0).getOrderId(), getBuilderItem.getOrderId());
    }

}

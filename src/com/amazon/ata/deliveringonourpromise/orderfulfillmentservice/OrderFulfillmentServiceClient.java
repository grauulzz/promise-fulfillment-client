package com.amazon.ata.deliveringonourpromise.orderfulfillmentservice;

import com.amazon.ata.deliveringonourpromise.IServiceClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.orderfulfillmentservice.OrderFulfillmentService;
import com.amazon.ata.orderfulfillmentservice.OrderPromise;

/**
 * The type Order fulfillment service client.
 */
public class OrderFulfillmentServiceClient implements IServiceClient {

    private final OrderFulfillmentService ofService;

    /**
     * Instantiates a new Order fulfillment service client.
     *
     * @param ofService the of service
     */
    public OrderFulfillmentServiceClient(OrderFulfillmentService ofService) {
        this.ofService = ofService;
    }

    @Override
    public Promise getPromiseByOrderItemId(String customerOrderItemId) {
        OrderPromise orderPromise = ofService.getOrderPromise(customerOrderItemId);

        if (null == orderPromise) {
            return null;
        }

        return Promise.builder()
                .withPromiseLatestArrivalDate(orderPromise.getPromiseLatestArrivalDate())
                .withCustomerOrderItemId(orderPromise.getCustomerOrderItemId())
                .withPromiseLatestShipDate(orderPromise.getPromiseLatestShipDate())
                .withPromiseEffectiveDate(orderPromise.getPromiseEffectiveDate())
                .withIsActive(orderPromise.isActive())
                .withPromiseProvidedBy(orderPromise.getPromiseProvidedBy())
                .withAsin(orderPromise.getAsin())
                .build();
    }

}

package com.amazon.ata.deliveringonourpromise;

import com.amazon.ata.deliveringonourpromise.types.Promise;

/**
 * The interface Service client.
 */
public interface IServiceClient {
    /**
     * Gets promise by order item id.
     *
     * @param customerOrderItemId the customer order item id
     * @return the promise by order item id
     */
    Promise getPromiseByOrderItemId(String customerOrderItemId);
}

package com.amazon.ata.deliveringonourpromise.dao;


/**
 * The interface Read only dao.
 *
 * @param <I> the type parameter
 * @param <O> the type parameter
 */
public interface ReadOnlyDao<I, O> {

    /**
     * Get object method to be implemented.
     *
     * @param orderId Order Id
     * @return Object abstracted object
     */
    O get(I orderId);
}

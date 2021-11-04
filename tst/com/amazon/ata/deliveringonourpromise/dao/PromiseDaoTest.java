package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.DeliveryPromiseServiceClient;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.order.OrderFieldValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class PromiseDaoTest {

    private PromiseDao dao;

    private final OrderManipulationAuthorityClient omaClient = App.getOrderManipulationAuthorityClient();
    private final DeliveryPromiseServiceClient dpsClient = App.getDeliveryPromiseServiceClient();

    private String shippedOrderItemId;
    private Promise shippedDeliveryPromise;


    private String deliveredOrderItemId;

    private Promise deliveredDeliveryPromise;
    private ZonedDateTime deliveredDeliveryDate;

    // participants: @BeforeEach means this method is run before each test method is executed, often setting up data +
    // an instance of the class under test.
    @BeforeEach
    private void setup() {
        // order fixtures: these are specific known test orders.
        // undelivered
        String shippedOrderId = "900-3746401-0000002";
        // delivered
        String deliveredOrderId = "900-3746401-0000003";

        // We're doing this (not isolating the DAO from dependencies) because we haven't covered mocking yet
        // Note that this logic depends on the above orders being single-item orders
        shippedOrderItemId = omaClient
                                 .getCustomerOrderByOrderId(shippedOrderId)
                                 .getCustomerOrderItemList()
                                 .get(0)
                                 .getCustomerOrderItemId();
        shippedDeliveryPromise = dpsClient.getPromiseByOrderItemId(shippedOrderItemId);

        deliveredOrderItemId = omaClient
                                   .getCustomerOrderByOrderId(deliveredOrderId)
                                   .getCustomerOrderItemList()
                                   .get(0)
                                   .getCustomerOrderItemId();
        deliveredDeliveryPromise = dpsClient.getPromiseByOrderItemId(deliveredOrderItemId);
        deliveredDeliveryDate = omaClient
                                    .getCustomerOrderByOrderId(deliveredOrderId)
                                    .getOrderShipmentList().get(0)
                                    .getDeliveryDate();

        dao = new PromiseDao(dpsClient, omaClient);
    }

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
        List<Promise> promises = dao.get("123");

        // THEN
        assertTrue(promises.isEmpty());
    }

    @Test
    public void get_validOrderItemId_returnsCorrectPromiseLatestArrivalDate() {
        // GIVEN delivered promise

        // WHEN
        List<Promise> promises = dao.get(deliveredOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertEquals(deliveredDeliveryPromise.getPromiseLatestArrivalDate(),
                     dpsPromise.getPromiseLatestArrivalDate()
        );
    }

    @Test
    public void get_validOrderItemId_returnsCorrectCustomerOrderItemId() {
        // GIVEN delivered promise

        // WHEN
        List<Promise> promises = dao.get(deliveredOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertEquals(deliveredDeliveryPromise.getCustomerOrderItemId(), dpsPromise.getCustomerOrderItemId());
    }

    @Test
    public void get_validOrderItemId_returnsCorrectLatestShipDate() {
        // GIVEN delivered promise

        // WHEN
        List<Promise> promises = dao.get(deliveredOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertEquals(deliveredDeliveryPromise.getPromiseLatestShipDate(), dpsPromise.getPromiseLatestShipDate());
    }

    @Test
    public void get_validOrderItemId_returnsCorrectPromiseEffectiveDate() {
        // GIVEN delivered promise

        // WHEN
        List<Promise> promises = dao.get(deliveredOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertEquals(deliveredDeliveryPromise.getPromiseEffectiveDate(), dpsPromise.getPromiseEffectiveDate());
    }

    @Test
    public void get_validOrderItemId_returnsCorrectIsActive() {
        // GIVEN delivered promise

        // WHEN
        List<Promise> promises = dao.get(deliveredOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertEquals(deliveredDeliveryPromise.isActive(), dpsPromise.isActive());
    }

    @Test
    public void get_validOrderItemId_returnsCorrectPromiseProvidedBy() {
        // GIVEN delivered promise

        // WHEN
        List<Promise> promises = dao.get(deliveredOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertEquals(deliveredDeliveryPromise.getPromiseProvidedBy(), dpsPromise.getPromiseProvidedBy());
    }

    @Test
    public void get_validOrderItemId_returnsCorrectAsin() {
        // GIVEN delivered promise

        // WHEN
        List<Promise> promises = dao.get(deliveredOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertEquals(deliveredDeliveryPromise.getAsin(), dpsPromise.getAsin());
    }

    @Test
    public void get_deliveredShipment_returnsDeliveryDate() {
        // GIVEN delivered promise

        // WHEN
        List<Promise> promises = dao.get(deliveredOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertEquals(deliveredDeliveryDate, dpsPromise.getDeliveryDate());
    }

    @Test
    public void get_undeliveredShipment_returnsNullDeliveryDate() {
        // GIVEN un-delivered promise

        // WHEN
        List<Promise> promises = dao.get(shippedOrderItemId);

        // THEN
        Promise dpsPromise = findPromiseFromSource(promises, "DPS");
        assertNotNull(dpsPromise);
        assertNull(dpsPromise.getDeliveryDate());
    }

    /**
     * Searches through the given Promises looking for a Promise from the provider indicated in promiseProvidedBy.
     *
     * @param promises the List of Promises to search through for a Promise from given provider
     * @param promiseProvidedBy String representation of the service provider (e.g. "DPS", "OFS")
     * @return the Promise from this list that came from the specified provider, if any exists; null otherwise
     */
    private Promise findPromiseFromSource(List<Promise> promises, String promiseProvidedBy) {
        for (Promise promise : promises) {
            if (promise.getPromiseProvidedBy().equals(promiseProvidedBy)) {
                return promise;
            }
        }

        return null;
    }

}

package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BREAD;
import static seedu.address.testutil.TypicalDeliveries.APPLE;
import static seedu.address.testutil.TypicalDeliveries.BREAD;
import static seedu.address.testutil.TypicalSuppliers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryTest {
    @Test
    public void isSameDelivery() {
        // same object -> returns true
        assertTrue(APPLE.isSameDelivery(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameDelivery(null));

        // different product name, all other attributes same -> returns false
        Delivery editedApple = new DeliveryBuilder(APPLE).withProduct("Iphone12").build();
        assertFalse(APPLE.isSameDelivery(editedApple));
    }
    @Test
    public void hasSameStatus_sameStatus_returnsTrue() {
        Delivery deliveryPending = new DeliveryBuilder().build();
        Delivery deliveryDelivered = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        Delivery deliveryCancelled = new DeliveryBuilder().withStatus(Status.CANCELLED).build();
        assertTrue(deliveryPending.hasSameStatus(Status.PENDING));
        assertTrue(deliveryDelivered.hasSameStatus(Status.DELIVERED));
        assertTrue(deliveryCancelled.hasSameStatus(Status.CANCELLED));
    }

    @Test
    public void hasSameStatus_differentStatus_returnsFalse() {
        Delivery deliveryPending = new DeliveryBuilder().build();
        Delivery deliveryDelivered = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        Delivery deliveryCancelled = new DeliveryBuilder().withStatus(Status.CANCELLED).build();
        assertFalse(deliveryPending.hasSameStatus(Status.DELIVERED));
        assertFalse(deliveryDelivered.hasSameStatus(Status.CANCELLED));
        assertFalse(deliveryCancelled.hasSameStatus(Status.PENDING));
    }
    @Test
    public void hasEarlierDateThan_laterDelivery_returnsTrue() {
        Delivery delivery = new DeliveryBuilder().withDeliveryTime("19-11-2022 08:00").build();
        assertTrue(delivery.hasEarlierDateThan(new DateTime("19-11-2022 08:55"))); //later by minute
        assertTrue(delivery.hasEarlierDateThan(new DateTime("19-11-2023 08:00"))); //later by year
        assertTrue(delivery.hasEarlierDateThan(new DateTime("19-12-2022 08:00"))); //later by month
        assertTrue(delivery.hasEarlierDateThan(new DateTime("20-11-2022 08:00"))); //later by day
    }
    @Test
    public void hasEarlierDateThan_earlierDelivery_returnsFalse() {
        Delivery delivery = new DeliveryBuilder().withDeliveryTime("19-12-2022 08:00").build();
        assertFalse(delivery.hasEarlierDateThan(new DateTime("19-12-2022 07:55"))); //earlier by minute
        assertFalse(delivery.hasEarlierDateThan(new DateTime("19-12-2021 08:00"))); //earlier by year
        assertFalse(delivery.hasEarlierDateThan(new DateTime("19-11-2022 08:00"))); //earlier by month
        assertFalse(delivery.hasEarlierDateThan(new DateTime("18-12-2022 08:00"))); //earlier by day
    }

    @Test
    public void equals() {
        Delivery delivery = new DeliveryBuilder().build();

        // same values -> returns true
        assertTrue(APPLE.equals(delivery));

        // same object -> returns true
        assertTrue(delivery.equals(delivery));

        // null -> returns false
        assertFalse(delivery.equals(null));

        // different type -> returns false
        assertFalse(delivery.equals("Abcdef"));

        // different delivery -> returns false
        assertFalse(delivery.equals(BREAD));
    }

    @Test
    public void equals_differentDateTime_returnsFalse() {
        // different dateTime -> returns false
        Delivery editedDelivery = new DeliveryBuilder().withDeliveryTime(VALID_DATE_BREAD).build();
        assertFalse(APPLE.equals(editedDelivery));
    }

    @Test
    public void equals_differentCost_returnsFalse() {
        // different cost -> returns false
        Delivery editedDelivery = new DeliveryBuilder().withCost(VALID_COST_BREAD).build();
        assertFalse(APPLE.equals(editedDelivery));
    }

    @Test
    public void equals_differentQuantity_returnsFalse() {
        // different quantity -> returns false
        Delivery editedDelivery = new DeliveryBuilder().withQuantity(VALID_QUANTITY_BREAD).build();
        assertFalse(APPLE.equals(editedDelivery));
    }
    @Test
    public void equals_differentStatus_returnsFalse() {
        // different status -> returns false
        Delivery editedDelivery = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        assertFalse(APPLE.equals(editedDelivery));

    }
    @Test
    public void equals_differentSupplier_returnsFalse() {
        // different sender -> returns false
        Delivery editedDelivery = new DeliveryBuilder().withSender(BOB).build();
        assertFalse(APPLE.equals(editedDelivery));
    }

    @Test
    public void equals_differentProduct_returnsFalse() {
        // different sender -> returns false
        Delivery editedDelivery = new DeliveryBuilder().withProduct(VALID_PRODUCT_BREAD).build();
        assertFalse(APPLE.equals(editedDelivery));
    }
}

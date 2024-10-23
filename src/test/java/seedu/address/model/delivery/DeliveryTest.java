package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BREAD;
import static seedu.address.testutil.TypicalDeliveries.APPLE;
import static seedu.address.testutil.TypicalDeliveries.BREAD;
import static seedu.address.testutil.TypicalPersons.BOB;

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
    public void hasSameStatus_sameStatus() {
        Delivery deliveryPending = new DeliveryBuilder().build();
        Delivery deliveryDelivered = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        Delivery deliveryCancelled = new DeliveryBuilder().withStatus(Status.CANCELLED).build();
        assertTrue(deliveryPending.hasSameStatus(Status.PENDING));
        assertTrue(deliveryDelivered.hasSameStatus(Status.DELIVERED));
        assertTrue(deliveryCancelled.hasSameStatus(Status.CANCELLED));
    }

    @Test
    public void hasSameStatus_differentStatus() {
        Delivery deliveryPending = new DeliveryBuilder().build();
        Delivery deliveryDelivered = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        Delivery deliveryCancelled = new DeliveryBuilder().withStatus(Status.CANCELLED).build();
        assertFalse(deliveryPending.hasSameStatus(Status.DELIVERED));
        assertFalse(deliveryDelivered.hasSameStatus(Status.CANCELLED));
        assertFalse(deliveryCancelled.hasSameStatus(Status.PENDING));
    }
    @Test
    public void hasEarlierDateThan_laterDelivery() {
        Delivery delivery = new DeliveryBuilder().withDeliveryTime("19-11-2022 08:00").build();
        assertTrue(delivery.hasEarlierDateThan(new DateTime("19-11-2022 08:55"))); //later by minute
        assertTrue(delivery.hasEarlierDateThan(new DateTime("19-11-2023 08:00"))); //later by year
        assertTrue(delivery.hasEarlierDateThan(new DateTime("19-12-2022 08:00"))); //later by month
        assertTrue(delivery.hasEarlierDateThan(new DateTime("20-11-2022 08:00"))); //later by day
    }
    @Test
    public void hasEarlierDateThan_earlierDelivery() {
        Delivery delivery = new DeliveryBuilder().withDeliveryTime("19-12-2022 08:00").build();
        assertFalse(delivery.hasEarlierDateThan(new DateTime("19-12-2022 07:55"))); //earlier by minute
        assertFalse(delivery.hasEarlierDateThan(new DateTime("19-12-2021 08:00"))); //earlier by year
        assertFalse(delivery.hasEarlierDateThan(new DateTime("19-11-2022 08:00"))); //earlier by month
        assertFalse(delivery.hasEarlierDateThan(new DateTime("18-12-2022 08:00"))); //earlier by day
    }

    @Test
    public void equals() {
        // same values -> returns true
        Delivery delivery = new DeliveryBuilder().build();

        assertTrue(APPLE.equals(delivery));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals("Abcdef"));

        // different delivery -> returns false
        assertFalse(APPLE.equals(BREAD));

        // different dateTime -> returns false
        Delivery editedDelivery = new DeliveryBuilder().withDeliveryTime(VALID_DATE_BREAD).build();
        assertFalse(APPLE.equals(editedDelivery));

        // different cost -> returns false
        editedDelivery = new DeliveryBuilder().withCost(VALID_COST_BREAD).build();
        assertFalse(APPLE.equals(editedDelivery));

        // different quantity -> returns false
        editedDelivery = new DeliveryBuilder().withQuantity(VALID_QUANTITY_BREAD).build();
        assertFalse(APPLE.equals(editedDelivery));

        // different status -> returns false
        editedDelivery = new DeliveryBuilder().withStatus(Status.DELIVERED).build();
        assertFalse(APPLE.equals(editedDelivery));

        // different sender -> returns false
        editedDelivery = new DeliveryBuilder().withSender(BOB).build();
        assertFalse(APPLE.equals(editedDelivery));
    }
}

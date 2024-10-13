package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Status;

/**
 * A utility class containing a list of {@code Delivery} objects to be used in tests.
 */
public class TypicalDeliveriesWithoutSender {

    public static final Delivery APPLE = new DeliveryBuilder().buildWithNullSender();
    public static final Delivery BREAD = new DeliveryBuilder().withProduct("sunshineBread")
            .withDeliveryTime("12-10-2024 17:30")
            .withStatus(Status.PENDING)
            .withCost("150")
            .withQuantity("100 units")
            .buildWithNullSender();

    public static final Delivery CAN = new DeliveryBuilder().withProduct("cannedDrinks")
            .withDeliveryTime("03-03-2025 10:30")
            .withStatus(Status.PENDING)
            .withCost("200")
            .withQuantity("250 units")
            .buildWithNullSender();

    private TypicalDeliveriesWithoutSender() {} // prevents instantiation

    public static List<Delivery> getTypicalDeliveriesWithoutSender() {
        return new ArrayList<>(Arrays.asList(APPLE, BREAD, CAN));
    }
}

package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Status;

/**
 * A utility class containing a list of {@code Delivery} objects to be used in tests.
 */
public class TypicalDeliveries {

    public static final Delivery APPLE = new DeliveryBuilder().build();
    public static final Delivery BREAD = new DeliveryBuilder().withProduct("bread")
            .withSender(TypicalPersons.BENSON)
            .withDeliveryTime("12-10-2024 17:30")
            .withStatus(Status.PENDING)
            .withCost("150")
            .withQuantity("100 units")
            .withSupplierIndex("2")
            .build();

    public static final Delivery CAN = new DeliveryBuilder().withProduct("cannedDrinks")
            .withSender(TypicalPersons.CARL)
            .withDeliveryTime("03-03-2025 10:30")
            .withStatus(Status.PENDING)
            .withCost("200")
            .withQuantity("250 units")
            .build();

    private TypicalDeliveries() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical deliveries.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Delivery delivery : getTypicalDeliveries()) {
            ab.addDelivery(delivery);
        }
        return ab;
    }

    public static List<Delivery> getTypicalDeliveries() {
        return new ArrayList<>(Arrays.asList(APPLE, BREAD, CAN));
    }
}

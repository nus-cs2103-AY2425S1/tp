package seedu.address.testutil;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryWrapper;
import seedu.address.model.delivery.SupplierIndex;
/**
 * A utility class containing a list of {@code DeliveryWrapper} objects to be used in tests.
 */
public class TypicalDeliveryWrappers {

    public static final DeliveryWrapper APPLE_WRAPPER = new DeliveryWrapper(TypicalDeliveries.APPLE,
            new SupplierIndex("1"));

    public static final DeliveryWrapper BREAD_WRAPPER = new DeliveryWrapper(TypicalDeliveries.BREAD,
            new SupplierIndex("1"));


    public static final DeliveryWrapper CAN_WRAPPER = new DeliveryWrapper((TypicalDeliveries.CAN),
            new SupplierIndex("3"));
    public static final DeliveryWrapper DURIAN_WRAPPER = new DeliveryWrapper(TypicalDeliveries.DURIAN,
            new SupplierIndex("1"));

    private TypicalDeliveryWrappers() {} // prevents instantiation
    public static DeliveryWrapper getNullWrapper() {
        Delivery delivery = new DeliveryBuilder().withSender(null).build();
        return new DeliveryWrapper(delivery, new SupplierIndex("1"));
    }
}

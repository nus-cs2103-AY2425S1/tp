package seedu.address.testutil;

import static seedu.address.testutil.TypicalDeliveries.APPLES;
import static seedu.address.testutil.TypicalDeliveries.BANANAS;
import static seedu.address.testutil.TypicalDeliveries.ORANGES;
import static seedu.address.testutil.TypicalDeliveries.PEARS;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryList;

/**
 * A utility class to help with building DeliveryList objects.
 */
public class DeliveryListBuilder {
    private List<Delivery> deliveryList = Arrays.asList(APPLES, ORANGES, BANANAS, PEARS);

    /**
     * Constructor to do nothing
     */
    public DeliveryListBuilder() {}

    /**
     * Adds deliveries to the arraylist of deliveries
     * @param delivery Delivery object to be added
     */
    public DeliveryListBuilder withNewDelivery(Delivery delivery) {
        deliveryList.add(delivery);
        return this;
    }

    /**
     * Builds the {@code DeliveryList} object
     *
     * @return The built DeliveryList object.
     */
    public DeliveryList build() {
        DeliveryList deliveryList = new DeliveryList();
        for (Delivery delivery: this.deliveryList) {
            deliveryList.add(delivery);
        }
        return deliveryList;
    }

}

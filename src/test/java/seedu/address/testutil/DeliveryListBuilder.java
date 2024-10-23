package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryList;

/**
 * A utility class to help with building DeliveryList objects.
 */
public class DeliveryListBuilder {
    private ArrayList<Delivery> deliveryArrayList;

    /**
     * Constructor to do nothing
     */
    public DeliveryListBuilder() {}

    /**
     * Adds deliveries to the arraylist of deliveries
     * @param delivery Delivery object to be added
     */
    public DeliveryListBuilder withNewDelivery(Delivery delivery) {
        deliveryArrayList.add(delivery);
        return this;
    }

    /**
     * Builds the {@code DeliveryList} object
     *
     * @return The built DeliveryList object.
     */
    public DeliveryList build() {
        DeliveryList deliveryList = new DeliveryList();
        for (Delivery delivery: deliveryArrayList) {
            deliveryList.add(delivery);
        }
        return deliveryList;
    }

}

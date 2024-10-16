package seedu.address.testutil;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryList;

import java.util.ArrayList;

public class DeliveryListBuilder {
    private ArrayList<Delivery> deliveryArrayList;

    public DeliveryListBuilder() {}

    public DeliveryListBuilder withNewDelivery(Delivery delivery) {
        deliveryArrayList.add(delivery);
        return this;
    }

    public DeliveryList build() {
        DeliveryList deliveryList = new DeliveryList();
        for (Delivery delivery: deliveryArrayList) {
            deliveryList.add(delivery);
        }
        return deliveryList;
    }

}

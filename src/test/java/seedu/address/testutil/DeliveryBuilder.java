package seedu.address.testutil;

import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.Time;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * A utility class to help with building Delivery objects.
 */
public class DeliveryBuilder {

    public static final String DEFAULT_PRODUCT = "Iphone16Pro";
    public static final String DEFAULT_DELIVERY_TIME = "10-10-2024 16:00";
    public static final String DEFAULT_COST = "200000";
    public static final String DEFAULT_QUANTITY = "200 units";

    private Product product;
    private Person sender; // CHANGE TO SUPPLIER LATER ON
    private Status status;
    private Time deliveryTime;
    private Cost cost;
    private Quantity quantity;

    /**
     * Creates a {@code DeliveryBuilder} with the default details.
     */
    public DeliveryBuilder() {
        product = new Product(DEFAULT_PRODUCT);
        sender = TypicalPersons.ALICE;
        deliveryTime = new Time(DEFAULT_DELIVERY_TIME);
        status = Status.PENDING;
        cost = new Cost(DEFAULT_COST);
        quantity = new Quantity(DEFAULT_QUANTITY);
    }

    /**
     * Initializes the DeliveryBuilder with the data of {@code deliveryToCopy}.
     */
    public DeliveryBuilder(Delivery deliveryToCopy) {
        product = deliveryToCopy.getDeliveryProduct();
        sender = deliveryToCopy.getDeliverySender();
        deliveryTime = deliveryToCopy.getDeliveryDate();
        status = deliveryToCopy.getDeliveryStatus();
        cost = deliveryToCopy.getDeliveryCost();
        quantity = deliveryToCopy.getDeliveryQuantity();

    }

    /**
     * Sets the {@code Product} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withProduct(String product) {
        this.product = new Product(product);
        return this;
    }

    /**
     * Sets the {@code Sender} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withSender(Person sender) {
        this.sender = sender;
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withDeliveryTime(String time) {
        this.deliveryTime = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    public Delivery build() {
        return new Delivery(product, sender, status, deliveryTime, cost, quantity);
    }

}

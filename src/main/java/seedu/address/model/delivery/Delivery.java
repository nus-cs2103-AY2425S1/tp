package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Represents a Delivery in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Delivery {

    private final Product product;
    private Person sender; // CHANGE TO SUPPLIER LATER ON
    private Status status;
    private final Time deliveryTime;
    private final Cost cost;
    private final Quantity quantity;
    private final SupplierIndex supplierIndex;

    /**
     * Every field must be present and not null.
     */
    public Delivery(Product product, Person sender, Status status, Time deliveryTime, Cost cost, Quantity quantity) {
        requireAllNonNull(product, sender, status, deliveryTime, cost, quantity);
        this.product = product;
        this.sender = sender;
        this.status = status;
        this.deliveryTime = deliveryTime;
        this.cost = cost;
        this.quantity = quantity;
        //might need to change this
        this.supplierIndex = null;
    }
    //might need to change this

    /**
     * Creates an instance of Delivery object with sender set to null and initialised
     * when the execute method of AddDevlieryCommand is called.
     *
     * @param product Product being delivered.
     * @param sender Supplier object in charge of delivery
     * @param status Status of delivery which is set to Status.PENDING initially.
     * @param deliveryTime Represents a LocalDateTime object with Date and time of delivery.
     * @param cost Cost of delivery.
     * @param quantity Quantity of product to be delivered.
     * @param supplierIndex Index of supplier inside the UniqueDeliveryList.
     */
    public Delivery(Product product, Person sender, Status status, Time deliveryTime, Cost cost,
                    Quantity quantity, SupplierIndex supplierIndex) {
        requireAllNonNull(product, status, deliveryTime, cost, quantity, supplierIndex);
        this.product = product;
        this.sender = sender;
        this.status = status;
        this.deliveryTime = deliveryTime;
        this.cost = cost;
        this.quantity = quantity;
        this.supplierIndex = supplierIndex;
    }
    public SupplierIndex getSupplierIndex() {
        return this.supplierIndex;
    }

    public Product getDeliveryProduct() {
        return this.product;
    }

    public Person getDeliverySender() {
        return this.sender;
    }

    public Status getDeliveryStatus() {
        return this.status;
    }

    public Time getDeliveryDate() {
        return this.deliveryTime;
    }

    public Cost getDeliveryCost() {
        return this.cost;
    }

    public Quantity getDeliveryQuantity() {
        return this.quantity;
    }
    public void setDeliverySender(Person sender) {
        this.sender = sender;
    }


    /**
     * Returns true if both deliveries have the same package details, sender.
     */
    public boolean isSameDelivery(Delivery otherDelivery) {
        if (otherDelivery == this) {
            return true;
        }

        // what are the conditions on same deliveries (weaker equality)
        return otherDelivery != null
                && otherDelivery.getDeliveryDate().equals(getDeliveryDate())
                && otherDelivery.getDeliveryProduct().equals(getDeliveryProduct())
                && otherDelivery.getDeliverySender().equals(getDeliverySender());
    }

    /**
     * Returns true if both deliveries have the same identity and data fields.
     */

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Delivery)) {
            return false;
        }
        Delivery otherDelivery = (Delivery) other;
        boolean isSameSender = this.sender == null || otherDelivery.sender == null
                             ? otherDelivery.sender == this.sender
                             : this.sender.equals(otherDelivery.sender);
        return product.equals(otherDelivery.product)
                && status.equals(otherDelivery.status)
                && deliveryTime.equals(otherDelivery.deliveryTime)
                && cost.equals(otherDelivery.cost)
                && quantity.equals(otherDelivery.quantity)
                && isSameSender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, sender, status, deliveryTime, cost, quantity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("product", product)
                .add("sender", sender)
                .add("status", status)
                .add("deliveryDate", deliveryTime)
                .add("cost", cost)
                .add("quantity", quantity)
                .toString();
    }
}

package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.supplier.Supplier;

/**
 * Represents a wrapper class for Delivery.
 */
public class DeliveryWrapper {
    private Delivery delivery;
    private SupplierIndex supplierIndex;

    /**
     * Creates a DeliveryWrapper to pair the specified {@code Delivery} to a SupplierIndex.
     *
     * @param delivery The new delivery to pair with supplier.
     * @param supplierIndex The index of supplier in displayed list currently.
     */
    public DeliveryWrapper(Delivery delivery, SupplierIndex supplierIndex) {
        requireAllNonNull(delivery, supplierIndex);
        this.delivery = delivery;
        this.supplierIndex = supplierIndex;
    }

    /**
     * Returns the delivery instance wrapped.
     *
     * @return Delivery to be added.
     */
    public Delivery getDelivery() {
        return this.delivery;
    }

    /**
     * Returns the supplier index wrapped.
     *
     * @return SupplierIndex representing supplier to pair delivery with.
     */
    public SupplierIndex getSupplierIndex() {
        return this.supplierIndex;
    }

    /**
     * Sets the Supplier field of Delivery to the input supplier.
     *
     * @param supplier Supplier instance to be paired with delivery.
     */
    public void setDeliverySupplier(Supplier supplier) {
        requireAllNonNull(supplier);
        assert supplier != null;
        this.delivery.setDeliverySender(supplier);
    }
    /**
     * Returns true if Delivery and SupplierIndex of both objects are same.
     *
     * @param other Object to be compared with.
     * @return True if object is an instance of DeliveryWrapper and both
     *         Delivery objects and SupplierIndex objects are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryWrapper)) {
            return false;
        }

        DeliveryWrapper otherDeliveryWrapper = (DeliveryWrapper) other;
        return this.delivery.equals(otherDeliveryWrapper.delivery)
                && this.supplierIndex.equals(otherDeliveryWrapper.supplierIndex);
    }
}

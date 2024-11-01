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
     * Creates a DeliveryWrapper to pair the specified {@code Delivery} to a supplier
     *
     * @param delivery The new delivery to pair with supplier.
     * @param supplierIndex The index of supplier in addressbook currently.
     */
    public DeliveryWrapper(Delivery delivery, SupplierIndex supplierIndex) {
        requireAllNonNull(delivery, supplierIndex);
        this.delivery = delivery;
        this.supplierIndex = supplierIndex;
    }

    /**
     * Returns the delivery instance wrapped.
     *
     * @return Delivery to be paired.
     */
    public Delivery getDelivery() {
        return this.delivery;
    }

    /**
     * Returns the supplier index wrapped.
     *
     * @return Supplier index to pair delivery with.
     */
    public SupplierIndex getSupplierIndex() {
        return this.supplierIndex;
    }

    /**
     * Sets the supplier field of delivery to the provided supplier
     *
     * @param supplier Supplier instance to be paired with delivery.
     */
    public void setDeliverySupplier(Supplier supplier) {
        requireAllNonNull(supplier);
        assert supplier != null;
        this.delivery.setDeliverySender(supplier);
    }

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

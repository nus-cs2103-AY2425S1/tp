package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.UniqueDeliveryList;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.UniqueSupplierList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameSupplier comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueSupplierList suppliers;
    private final UniqueDeliveryList deliveries;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        suppliers = new UniqueSupplierList();
        deliveries = new UniqueDeliveryList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Suppliers and Deliveries in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the supplier list with {@code suppliers}.
     * {@code suppliers} must not contain duplicate suppliers.
     */
    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers.setSuppliers(suppliers);
    }

    /**
     * Replaces the contents of the delivery list with {@code deliveries}.
     * {@code deliveries} must not contain duplicate deliveries.
     */
    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries.setDeliveries(deliveries);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setDeliveries(newData.getDeliveryList());
        setSuppliers(newData.getSupplierList());
    }

    //// delivery-level operations

    /**
     * Returns true if a delivery with the same identity as {@code delivery} exists in the address book.
     */
    public boolean hasDelivery(Delivery delivery) {
        requireNonNull(delivery);
        return deliveries.contains(delivery);
    }

    /**
     * Adds a delivery to the address book.
     * The delivery must not already exist in the address book.
     */
    public void addDelivery(Delivery d) {
        deliveries.add(d);
    }


    /**
     * Removes {@code key} from the delivery list in the {@code AddressBook}.
     * {@code key} must exist in the delivery list of the address book.
     */
    public void removeDelivery(Delivery key) {
        deliveries.remove(key);
    }

    /**
     * Replaces the given delivery {@code target} in the list with {@code editedDelivery}.
     * {@code target} must exist in the address book.
     * The delivery identity of {@code editedDelivery} must not be the same as
     * another existing delivery in the address book.
     */
    public void setDelivery(Delivery target, Delivery updatedDelivery) {
        requireNonNull(target);
        requireNonNull(updatedDelivery);

        deliveries.setDelivery(target, updatedDelivery);
    }

    //// supplier-level operations

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the address book.
     */
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return suppliers.contains(supplier);
    }

    /**
     * Adds a supplier to the address book.
     * The supplier must not already exist in the address book.
     */
    public void addSupplier(Supplier p) {
        suppliers.add(p);
    }

    /**
     * Replaces the given supplier {@code target} in the list with {@code editedSupplier}.
     * {@code target} must exist in the address book.
     * The supplier identity of {@code editedSupplier}
     * must not be the same as another existing supplier in the address book.
     */
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireNonNull(editedSupplier);

        suppliers.setSupplier(target, editedSupplier);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeSupplier(Supplier key) {
        suppliers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("suppliers", suppliers)
                .toString();
    }

    @Override
    public ObservableList<Supplier> getSupplierList() {
        return suppliers.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Delivery> getDeliveryList() {
        return deliveries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return suppliers.equals(otherAddressBook.suppliers);
    }

    @Override
    public int hashCode() {
        return suppliers.hashCode();
    }
}

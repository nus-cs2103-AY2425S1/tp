package seedu.sellsavvy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.customer.UniqueCustomerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCustomer comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueCustomerList customers;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        customers = new UniqueCustomerList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Customers in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the customer list with {@code customers}.
     * {@code customers} must not contain duplicate customers.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers.setCustomers(customers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setCustomers(newData.getCustomerList());
    }

    //// customer-level operations

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customers.contains(customer);
    }

    /**
     * Returns true if there is a different customer with the similar name
     * as given {@code Customer} exists in the address book.
     */
    public boolean hasSimilarCustomer(Customer customer) {
        requireNonNull(customer);
        return customers.hasSimilarCustomer(customer);
    }

    /**
     * Adds a customer to the address book.
     * The customer must not already exist in the address book.
     */
    public void addCustomer(Customer p) {
        customers.add(p);
    }

    /**
     * Replaces the given customer {@code target} in the list with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer in the address book.
     */
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireNonNull(editedCustomer);

        customers.setCustomer(target, editedCustomer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCustomer(Customer key) {
        customers.remove(key);
    }

    /**
     * Creates a copy of all data in this {@code AddressBook}.
     */
    public AddressBook createCopy() {
        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setCustomers(customers.copyCustomers().asUnmodifiableObservableList());
        return newAddressBook;
    }

    /**
     * Returns a {@code Customer} in the {@code UniqueCustomerList} equivalent to target Customer given.
     */
    public Customer findEquivalentCustomer(Customer target) {
        assert target != null; // Caller should handle null targets before calling this method
        return customers.findEquivalentCustomer(target);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("customers", customers)
                .toString();
    }

    @Override
    public ObservableList<Customer> getCustomerList() {
        return customers.asUnmodifiableObservableList();
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
        return customers.equals(otherAddressBook.customers);
    }

    @Override
    public int hashCode() {
        return customers.hashCode();
    }

}

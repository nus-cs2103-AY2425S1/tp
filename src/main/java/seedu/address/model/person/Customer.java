package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.order.CustomerOrder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Customer in the address book.
 * Inherits from Person and adds additional fields for Customer-specific information.
 */
public class Customer extends Person {

    private final List<CustomerOrder> openCustomerOrders; // Stores a list of open/unfulfilled customer orders.
    private final Information information; // Stores additional information about the customer.

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address,
                    Information information, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        this.information = information;
        this.openCustomerOrders = new ArrayList<>();
    }

    public List<CustomerOrder> getOpenCustomerOrders() {
        return openCustomerOrders;
    }

    public void addCustomerOrder(CustomerOrder customerOrder) {
        openCustomerOrders.add(customerOrder);
    }

    public void removeCustomerOrder(CustomerOrder customerOrder) {
        openCustomerOrders.remove(customerOrder);
    }

    public Information getInformation() {
        return information;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return super.equals(otherCustomer)
                && otherCustomer.getInformation().equals(getInformation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), information);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(super.toString());
        builder.append(" Information: ").append(getInformation());
        return builder.toString();
    }
}




package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.Remark;

/**
 * Represents a Customer with additional information beyond a standard Person.
 */
public class Customer extends Person {

    private final Information information;

    /**
     * Constructs a {@code Customer} with all required fields.
     *
     * @param name        The customer's name.
     * @param phone       The customer's phone.
     * @param email       The customer's email.
     * @param address     The customer's address.
     * @param information Additional information about the customer.
     * @param remark      Any remark associated with the customer.
     * @param tags        A set of tags related to the customer.
     */
    public Customer(Name name, Phone phone, Email email, Address address,
                    Information information, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        this.information = information;
    }

    /**
     * Retrieves the additional information about the customer.
     *
     * @return The {@code Information} object with additional details.
     */
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
        return super.equals(otherCustomer) && otherCustomer.getInformation().equals(getInformation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), information);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(super.toString());
        builder.append("\nInformation: ").append(getInformation());
        return builder.toString();
    }
}
package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Customer in the address book.
 * Inherits from Person and adds additional fields for Customer-specific information.
 */
public class Customer extends Person {

    private final String orderHistory;
    private final String favoriteItems;

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address,
                    DietaryPreference preference, Remark remark, Set<Tag> tags,
                    String orderHistory, String favoriteItems) {
        super(name, phone, email, address, preference, remark, tags);
        this.orderHistory = orderHistory;
        this.favoriteItems = favoriteItems;
    }

    public String getOrderHistory() {
        return orderHistory;
    }

    public String getFavoriteItems() {
        return favoriteItems;
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
                && otherCustomer.getOrderHistory().equals(getOrderHistory())
                && otherCustomer.getFavoriteItems().equals(getFavoriteItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderHistory, favoriteItems);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(super.toString());
        builder.append(" Order History: ")
                .append(getOrderHistory())
                .append(" Favorite Items: ")
                .append(getFavoriteItems());
        return builder.toString();
    }
}

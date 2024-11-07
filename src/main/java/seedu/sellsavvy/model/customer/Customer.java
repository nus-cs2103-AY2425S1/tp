package seedu.sellsavvy.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.tag.Tag;

/**
 * Represents a Customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Customer {
    private static final Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final OrderList orders;
    private final FilteredList<Order> filteredOrders;

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address, Set<Tag> tags, OrderList orders) {
        requireAllNonNull(name, phone, email, address, tags, orders);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.orders = orders;
        this.tags.addAll(tags);
        this.filteredOrders = new FilteredList<>(this.orders.asUnmodifiableObservableList());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
    public OrderList getOrderList() {
        return orders;
    }

    public Order getOrder(int index) {
        return orders.get(index);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both customers have the same name.
     * This defines a weaker notion of equality between two customers.
     */
    public boolean isSameCustomer(Customer otherCustomer) {
        if (otherCustomer == this) {
            return true;
        }

        return otherCustomer != null
                && otherCustomer.getName().equals(getName());
    }

    /**
     * Returns true if the customer's name is similar to other given customer's name.
     * Two customers' names are considered similar if they are the same without considering space and casing.
     */
    public boolean isSimilarTo(Customer otherCustomer) {
        return this.name.isSimilarTo(otherCustomer.name);
    }

    /**
     * Returns true if any of the tags is similar to other tags of the same customer.
     * Two tag names are considered similar if they are same without considering space and casing.
     */
    public boolean hasSimilarTags() {
        return tags.stream().anyMatch(
                tag -> tags.stream().anyMatch(
                        otherTag -> otherTag.isSimilarTo(tag) && tag != otherTag
                )
        );
    }

    /**
     * Returns the order list as an unmodifiable {@code FilteredList}.
     */
    public FilteredList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    /**
     * Returns true if the filtered order list is filtered under certain conditions.
     */
    public boolean areOrdersFiltered() {
        return filteredOrders.getPredicate() != PREDICATE_SHOW_ALL_ORDERS
                && filteredOrders.getPredicate() != null;
    }

    /**
     * Updates the filter of the order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredOrderList(Predicate<? super Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    /**
     * Resets the filter of the order list to display all orders.
     */
    public void resetFilteredOrderList() {
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    /**
     * Returns the predicate of the customer's filtered order list.
     */
    public Predicate<? super Order> getOrderPredicate() {
        Predicate<? super Order> predicate = filteredOrders.getPredicate();
        return (predicate == null) ? PREDICATE_SHOW_ALL_ORDERS : predicate;
    }

    /**
     * Returns true if both customers have the same identity and data fields.
     * This defines a stronger notion of equality between two customers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return name.equals(otherCustomer.name)
                && phone.equals(otherCustomer.phone)
                && email.equals(otherCustomer.email)
                && address.equals(otherCustomer.address)
                && tags.equals(otherCustomer.tags)
                && orders.equals(otherCustomer.orders);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, orders);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("orders", orders)
                .toString();
    }

    /**
     * Creates a new copy of this {@code Customer} with new attributes.
     * Only the tags are not copied.
     */
    public Customer createCopy() {
        Name name = new Name(getName().toString());
        Address address = new Address(getAddress().toString());
        Phone phone = new Phone(getPhone().toString());
        Email email = new Email(getEmail().toString());
        Set<Tag> tags = getTags();
        OrderList orderList = getOrderList().createCopy();
        Customer customerCopy = new Customer(name, phone, email, address, tags, orderList);

        if (filteredOrders.getPredicate() == null) {
            return customerCopy;
        }

        Predicate<? super Order> filterPredicate = filteredOrders.getPredicate();
        customerCopy.updateFilteredOrderList(filterPredicate);
        return customerCopy;
    }
}

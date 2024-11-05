package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.order.Order;
import seedu.address.model.product.Ingredients;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Remark;

/**
 * Represents a person with contact details, address, remark, tags, and orders.
 * Supports customer and supplier roles through subclasses.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Order> orders = new ArrayList<>();

    /**
     * Constructs a {@code Person} with all required fields.
     *
     * @param name    The name of the person.
     * @param phone   The person's phone number.
     * @param email   The person's email.
     * @param address The address of the person.
     * @param remark  Any remark associated with the person.
     * @param tags    A set of tags related to the person.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
    }

    // Copy constructor
    public Person(Person person) {
        this.name = person.name;
        this.phone = person.phone;
        this.email = person.email;
        this.address = person.address;
        this.remark = person.remark;
        this.tags.addAll(person.tags);
    }

    /**
     * Retrieves the orders associated with this person as a formatted string.
     *
     * @return A formatted string of all orders.
     */
    public String getOrders() {
        StringBuilder builder = new StringBuilder();
        for (Order order : orders) {
            builder.append(order.toString()).append("\n");
        }
        return builder.toString();
    }

    /**
     * Adds an order to the person's list of orders.
     *
     * @param order The order to be added.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes a specific order from the person's list of orders.
     *
     * @param order The order to be removed.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Creates a guest {@code Person} with minimal information (name and phone).
     *
     * @param n The guest's name.
     * @param p The guest's phone.
     * @return A {@code Customer} instance with default values for other fields.
     */
    public static Person getGuest(Name n, Phone p) {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Customer"));
        return new Customer(n, p, new Email(), new Address(), new Information(),
                new Remark(), tags);
    }

    /**
     * Creates a supplier {@code Person} with minimal information (name and phone).
     *
     * @param n The supplier's name.
     * @param p The supplier's phone.
     * @return A {@code Supplier} instance with default values for other fields.
     */
    public static Person getSupplier(Name n, Phone p) {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Supplier"));
        return new Supplier(n, p, new Email(), new Address(), new Ingredients(new ArrayList<>()),
                new Remark(), tags);
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

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable view of the person's tags.
     *
     * @return An unmodifiable set of tags.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Checks if two persons have the same phone number, defining a weaker notion of equality.
     *
     * @param otherPerson The other person to compare with.
     * @return {@code true} if the phone numbers match; {@code false} otherwise.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Checks if two persons have the same identity and data fields, defining a stronger notion of equality.
     *
     * @param other The other person to compare with.
     * @return {@code true} if all fields match; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Person)) {
            return false;
        }
        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Remark: ").append(getRemark());

        return builder.toString();
    }
}
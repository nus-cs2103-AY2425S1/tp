package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.order.Order;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Comparable<Person> {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final HashMap<Order, Integer> orderFrequency;

    // Data fields
    private final Address address;
    private final PostalCode postalCode;
    private final Set<Tag> tags = new HashSet<>();
    private final Boolean isArchived;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, PostalCode postalCode, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, postalCode, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.tags.addAll(tags);
        this.orderFrequency = new HashMap<>();
        this.isArchived = false;
    }

    /**
     * Every field with orderFrequency must be present and not null
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  PostalCode postalCode, Set<Tag> tags, HashMap<Order, Integer> orders) {
        requireAllNonNull(name, phone, email, address, tags, orders);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.postalCode = postalCode;
        this.orderFrequency = orders;
        this.isArchived = false;
    }

    /**
     * Every field with field must be present and not null with isArchived
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  PostalCode postalCode, Set<Tag> tags, Boolean isArchived) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.postalCode = postalCode;
        this.orderFrequency = new HashMap<>();
        this.isArchived = isArchived;
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

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public HashMap<Order, Integer> getOrderFrequency() {
        return this.orderFrequency;
    }

    public boolean isArchived() {
        return isArchived;
    }

    /**
     * Increase the frequency of an order by 1 for the customer
     * @param order The order to increase its frequency
     */
    public void putOrder(Order order) {
        this.orderFrequency.merge(order, 1, Integer::sum);
    }

    /**
     * Remove order frequency record for order
     * @param order The order to remove
     */
    public void removeOrder(Order order) {
        this.orderFrequency.remove(order);
    }

    private int getTotalOrderFrequencyCount() {
        int sum = 0;
        for (Map.Entry<Order, Integer> entry: this.orderFrequency.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if the person has all the tags in the provided tag list.
     *
     * @param tagList The list of tags to check against the person's tags.
     * @return true if the person has all the tags in the tagList, false otherwise.
     */
    public boolean hasAllTags(Set<Tag> tagList) {
        requireNonNull(tagList);

        return tags.containsAll(tagList);
    }


    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Person with the largest order frequency count are at the front
     * @param rhs the object to be compared.
     * @return -1 if rhs has lower order frequency count, 0 if rhs has same order frequency count, 1 otherwise
     */
    @Override
    public int compareTo(Person rhs) {
        if (this.getTotalOrderFrequencyCount() < rhs.getTotalOrderFrequencyCount()) {
            return 1;
        } else if (this.getTotalOrderFrequencyCount() > rhs.getTotalOrderFrequencyCount()) {
            return -1;
        }
        return 0;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && postalCode.equals(otherPerson.postalCode)
                && orderFrequency.equals(otherPerson.orderFrequency)
                && isArchived == otherPerson.isArchived;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, postalCode, tags, orderFrequency, isArchived);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("postalCode", postalCode)
                .add("tags", tags)
                .add("orders", orderFrequency)
                .add("isArchived", isArchived)
                .toString();
    }

}

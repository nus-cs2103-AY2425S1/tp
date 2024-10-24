package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryId;
import seedu.address.model.delivery.DeliveryList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Role role;

    //Temporary initialisation for worker
    private Worker worker = new Worker(new HashSet<>(Arrays.asList(new DeliveryId(), new DeliveryId())));

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final DeliveryList deliveryList = new DeliveryList();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Role role, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        this.tags.addAll(tags);
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

    public Role getRole() {
        return role;
    }

    public Worker getWorker() {
        return worker;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the  {@code DeliveryList} of a {@code Person} as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Delivery> getUnmodifiableDeliveryList() {
        return deliveryList.asUnmodifiableObservableList();
    }

    /**
     * Returns the  {@code DeliveryList} of a {@code Person}.
     */
    public DeliveryList getDeliveryList() {
        return deliveryList;
    }

    /**
     * Returns the number of delivery in the deliveryList
     */
    public int getDeliveryListSize() {
        return deliveryList.size();
    }

    /**
     * Sets the delivery list of this person.
     * <p>
     * Mainly used when loading a person's information from storage.
     */
    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList.setDeliveries(deliveryList);
    }

    /**
     * Adds the delivery into the delivery list of this person.
     */
    public void addDelivery(Delivery delivery) {
        deliveryList.add(delivery);
    }

    /**
     * Adds the delivery into the delivery list of this person at the specified index.
     */
    public void addDelivery(Index targetIndex, Delivery delivery) {
        deliveryList.add(targetIndex, delivery);
    }

    /**
     * Remove the delivery from the delivery list of this person.
     */
    public void deleteDelivery(Index deliveryIndex) {
        deliveryList.remove(deliveryIndex);
    }

    /**
     * Replaces the given delivery {@code target} in the list with {@code editedDelivery}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedDelivery} must not be the same as another existing delivery in the list.
     */
    public void setDelivery(Delivery target, Delivery editedDelivery) {
        deliveryList.setDelivery(target, editedDelivery);
    }

    /**
     * Add the given delivery {@code unarchivedDelivery} to the list at {@code targetIndex}.
     * {@code targetIndex} must be a valid index in the deliveryList.
     */
    public void unarchiveDelivery(Index targetIndex, Delivery unarchivedDelivery) {
        deleteDelivery(targetIndex);
        addDelivery(getFirstArchivedIndex(), unarchivedDelivery);
    }

    /**
     * Returns the index of the first archived delivery in the list.
     */
    public Index getFirstArchivedIndex() {
        return deliveryList.getFirstArchivedIndex();
    }

    /**
     * Sets the Worker for this Person
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
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
     * Returns true if both persons have the same email
     */
    public boolean isSamePhone(Person otherPerson) {
        return otherPerson != null && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same email
     */
    public boolean isSameEmail(Person otherPerson) {
        return otherPerson != null && otherPerson.getEmail().equals(getEmail());
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
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}

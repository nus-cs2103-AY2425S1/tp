package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
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
    private final RoomNumber roomNumber;

    // Data fields
    private final Address address;
    private final EmergencyContact emergencyContact;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * AB3 basic constructor.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roomNumber = null;
        this.address = address;
        this.emergencyContact = new EmergencyContact(new Name("Aiken"), new Phone("12345678"));
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor that includes roomnumber
     */
    public Person(Name name, Phone phone, Email email, RoomNumber roomNumber, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roomNumber = roomNumber;
        this.address = address;
        this.emergencyContact = new EmergencyContact(new Name("Aiken"), new Phone("12345678"));
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

    public RoomNumber getRoomNumber() {
        return roomNumber; }

    public Address getAddress() {
        return address;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
     * Returns true if both persons have the same number.
     * This checks if the address book already contains someone with the same number.
     */
    public boolean isSameNumber(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getPhone().equals(getPhone());
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
                && (roomNumber == null ? otherPerson.roomNumber == null : roomNumber.equals(otherPerson.roomNumber))
                && address.equals(otherPerson.address)
                && emergencyContact.equals(otherPerson.emergencyContact)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, roomNumber, address, emergencyContact, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("room number", roomNumber)
                .add("address", address)
                .add("emergencyContact", emergencyContact)
                .add("tags", tags)
                .toString();
    }

}

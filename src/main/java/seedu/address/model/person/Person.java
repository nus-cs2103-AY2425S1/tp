package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: non-optional details are present and not null, field values are validated, immutable.
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
    private final GradYear gradYear;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Basic constructor.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roomNumber = null;
        this.address = address;
        this.emergencyContact = null;
        this.gradYear = null;
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor that includes optional parameters.
     * Every field must be present and not null, except for optional parameters.
     * Non-optional params: name, phone, email, address, and tags.
     * Optional params: roomNumber and emergencyContact.
     */
    public Person(Name name, Phone phone, Email email, RoomNumber roomNumber,
                  Address address, EmergencyContact emergencyContact, GradYear gradYear, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roomNumber = roomNumber;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.gradYear = gradYear;
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

    public Optional<RoomNumber> getRoomNumber() {
        return Optional.ofNullable(roomNumber);
    }

    public Address getAddress() {
        return address;
    }

    public Optional<EmergencyContact> getEmergencyContact() {
        return Optional.ofNullable(emergencyContact);
    }

    public Optional<Name> getEmergencyContactName() {
        return Optional.ofNullable(emergencyContact).flatMap(EmergencyContact::getName);
    }

    public Optional<Phone> getEmergencyContactPhone() {
        return Optional.ofNullable(emergencyContact).flatMap(EmergencyContact::getPhone);
    }

    public Optional<GradYear> getGradYear() {
        return Optional.ofNullable(gradYear);
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
    public boolean isSameName(Person otherPerson) {
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
                && otherPerson.getPhone().equals(this.getPhone());
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
                && (Objects.equals(roomNumber, otherPerson.roomNumber))
                && address.equals(otherPerson.address)
                && (Objects.equals(emergencyContact, otherPerson.emergencyContact))
                && (Objects.equals(gradYear, otherPerson.gradYear))
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, roomNumber, address, emergencyContact, gradYear, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("room number", roomNumber)
                .add("address", address)
                .add("emergency contact", emergencyContact)
                .add("graduation year", gradYear)
                .add("tags", tags)
                .toString();
    }

}

package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.exceptions.EmergencyContactNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    // LinkedHashSet preserves the order of emergency contacts such that
    // the index of the emergency contact can be reliably access by the Delete command.
    private final Set<EmergencyContact> emergencyContacts = new LinkedHashSet<>();
    private final Doctor doctor;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
            Set<EmergencyContact> emergencyContacts, Doctor doctor, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, emergencyContacts, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.emergencyContacts.addAll(emergencyContacts);
        this.doctor = doctor;
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
    public EmergencyContact getFirstEmergencyContact() {
        return emergencyContacts.iterator().next();
    }

    public Set<EmergencyContact> getEmergencyContacts() {
        return Collections.unmodifiableSet(emergencyContacts);
    }

    public EmergencyContact getEmergencyContact(Index oneBasedIndex)
            throws EmergencyContactNotFoundException {
        int i = oneBasedIndex.getZeroBased();
        for (EmergencyContact emergencyContact : emergencyContacts) {
            if (i == 0) {
                return emergencyContact;
            }
            i = i - 1;
        }
        throw new EmergencyContactNotFoundException();
    }

    /**
     * Returns a copy of emergencyContacts with
     * @param emergencyContactToRemove removed
     * @return a copy of emergencyContacts with {@code emergencyContactToRemove} removed
     */
    public Set<EmergencyContact> removeEmergencyContact(EmergencyContact emergencyContactToRemove) {
        Set<EmergencyContact> updatedEmergencyContacts = new LinkedHashSet<>();
        for (EmergencyContact emergencyContact : emergencyContacts) {
            if (emergencyContact.equals(emergencyContactToRemove)) {
                continue;
            } else {
                updatedEmergencyContacts.add(emergencyContact);
            }
        }
        return updatedEmergencyContacts;
    }

    public Boolean hasOnlyOneEmergencyContact() {
        return emergencyContacts.size() == 1;
    }

    /**
     * Checks if the specified emergency contact exists in the list of emergency contacts.
     *
     * @param emergencyContactToCheck The emergency contact to check for.
     * @return {@code true} if the emergency contact exists in the list, {@code false} otherwise.
     */
    public Boolean hasEmergencyContact(EmergencyContact emergencyContactToCheck) {
        for (EmergencyContact emergencyContact : emergencyContacts) {
            if (emergencyContact.equals((emergencyContactToCheck))) {
                return true;
            }
        }
        return false;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
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
                && emergencyContacts.equals(otherPerson.emergencyContacts)
                && doctor.equals(otherPerson.doctor)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, emergencyContacts, doctor, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("emergency contacts", emergencyContacts)
                .add("doctor", doctor)
                .add("tags", tags)
                .toString();
    }

}

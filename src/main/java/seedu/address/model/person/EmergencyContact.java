package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an Emergency Contact in the address book.
 * Guarantees: fields are present and not null, field values are validated.
 */
public class EmergencyContact {
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Relationship relationship;

    /**
     * Constructs an {@code EmergencyContact}.
     *
     * @param name A valid name.
     * @param phone A valid phone number.
     * @param relationship A valid relationship.
     */
    public EmergencyContact(Name name, Phone phone, Relationship relationship) {
        requireAllNonNull(name, phone, relationship);
        this.name = name;
        this.phone = phone;
        this.relationship = relationship;
    }

    /**
     * Returns the name of the emergency contact.
     *
     * @return Name of the emergency contact.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the phone number of the emergency contact.
     *
     * @return Phone number of the emergency contact.
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns the relationship with the emergency contact.
     *
     * @return Relationship with the emergency contact.
     */
    public Relationship getRelationship() {
        return relationship;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(EmergencyContact otherPerson) {
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
        if (!(other instanceof EmergencyContact)) {
            return false;
        }

        EmergencyContact otherPerson = (EmergencyContact) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && relationship.equals(otherPerson.relationship);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, relationship);
    }

    @Override
    public String toString() {
        return "Name: " + name + "; Phone: " + phone + "; Relationship: " + relationship + ";";
    }
}

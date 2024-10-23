package seedu.address.model.person;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Person's emergency contact in the address book.
 * Guarantees: field values are validated, immutable.
 * Note: all fields are optional.
 */
public class EmergencyContact {

    // Identity fields
    private final Name name;
    private final Phone phone;

    /**
     * Note: All fields are optional and can be null.
     */
    public EmergencyContact(Name name, Phone phone) {
        this.name = name;
        this.phone = phone;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    /**
     * Returns true if both emergency contacts have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EmergencyContact)) {
            return false;
        }

        EmergencyContact otherEmergencyContact = (EmergencyContact) other;
        return Objects.equals(name, otherEmergencyContact.name)
                && Objects.equals(phone, otherEmergencyContact.phone);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("(Name: ").append(name)
                .append("; Phone: ").append(phone)
                .append(")")
                .toString();
    }
}

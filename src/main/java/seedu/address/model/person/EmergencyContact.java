package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person's emergency contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class EmergencyContact {

    // Identity fields
    private final Name name;
    private final Phone phone;

    /**
     * Every field must be present and not null.
     */
    public EmergencyContact(Name name, Phone phone) {
        requireAllNonNull(name, phone);
        this.name = name;
        this.phone = phone;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
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
        return name.equals(otherEmergencyContact.name)
                && phone.equals(otherEmergencyContact.phone);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Name", name)
                .add("Phone", phone)
                .toString();
    }
}

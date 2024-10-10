package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's emergency contact in the address book.
 * Guarantees: immutable; is always valid
 */
public class EmergencyContact {
    public final String contactName;
    public final String contactNumber;

    /**
     * Constructs a {@code EmergencyContact}.
     *
     * @param contactName A valid name.
     * @param contactNumber A valid number.
     */
    public EmergencyContact(String contactName, String contactNumber) {
        requireNonNull(contactName);
        requireNonNull(contactNumber);
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return this.contactName;
    }

    public String getNumber() {
        return this.contactNumber;
    }
    @Override
    public String toString() {
        if (contactName.isEmpty() || contactNumber.isEmpty()) {
            return "No Emergency Contact";
        }
        return "Emergency Contact: " + contactName + ", " + contactNumber;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmergencyContact // instanceof handles nulls
                && contactName.equals(((EmergencyContact) other).contactName)
                && contactNumber.equals(((EmergencyContact) other).contactNumber)); // state check
    }
    @Override
    public int hashCode() {
        return (contactName + ": " + contactNumber).hashCode();
    }
}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's emergency contact in NovaCare.
 * Guarantees: immutable; is always valid
 */
public class EmergencyContact {
    public static final String NO_NAME = "No Name Entered";
    public static final String NO_NUMBER = "000";
    public final Name contactName;
    public final Phone contactNumber;

    /**
     * Constructs a {@code EmergencyContact}.
     *
     * @param contactName A valid name.
     * @param contactNumber A valid number.
     */
    public EmergencyContact(Name contactName, Phone contactNumber) {
        requireNonNull(contactName);
        requireNonNull(contactNumber);
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public Name getName() {
        return this.contactName;
    }

    public Phone getNumber() {
        return this.contactNumber;
    }

    @Override
    public String toString() {
        if (contactName.equals(new Name(NO_NAME)) || contactNumber.equals(new Phone(NO_NUMBER))) {
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

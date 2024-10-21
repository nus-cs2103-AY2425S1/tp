package seedu.address.model.person;

/**
 * Represents a Person's emergency contact in the address book.
 */
public class EmergencyContact {

    public static final String MESSAGE_CONSTRAINTS = Phone.MESSAGE_CONSTRAINTS;

    public static final String VALIDATION_REGEX = Phone.VALIDATION_REGEX;

    public final Phone emergencyContact;

    /**
     * Constructs a {@code EmergencyContact}.
     *
     * @param emergencyContact A valid emergency contact.
     */
    public EmergencyContact(String emergencyContact) {
        this.emergencyContact = new Phone(emergencyContact);
    }

    /**
     * Returns true if a given string is a valid emergency contact.
     */
    public static boolean isValidEmergencyContact(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return emergencyContact.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EmergencyContact)) {
            return false;
        }

        EmergencyContact otherEmergencyContact = (EmergencyContact) other;
        return emergencyContact.equals(otherEmergencyContact.emergencyContact);
    }

}

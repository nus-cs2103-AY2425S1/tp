package seedu.address.model.student;

/**
 * Represents a Student's emergency contact number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class EmergencyContact extends Phone {
    /**
     * Constructs a {@code EmergencyContact}.
     *
     * @param phone A valid phone number.
     */
    public EmergencyContact(String phone) {
        super(phone);
    }
}

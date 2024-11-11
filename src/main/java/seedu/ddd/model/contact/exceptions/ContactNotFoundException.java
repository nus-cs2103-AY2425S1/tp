package seedu.ddd.model.contact.exceptions;

/**
 * Signals that the operation is unable to find the specified contact.
 */
public class ContactNotFoundException extends RuntimeException {
    public static final String CONTACT_NOT_FOUND_MESSAGE = "Contact specified cannot be found";
    public ContactNotFoundException() {
        super(CONTACT_NOT_FOUND_MESSAGE);
    }
}

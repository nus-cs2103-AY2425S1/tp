package seedu.ddd.model.contact.exceptions;

/**
 * Signals that the operation will result in duplicate Contacts
 * (Contacts are considered duplicates if they have the same identity).
 */
public class DuplicateContactException extends RuntimeException {
    public static final String DUPLICATE_CONTACT_MESSAGE = "Operation would result in duplicate contacts";
    public DuplicateContactException() {
        super(DUPLICATE_CONTACT_MESSAGE);
    }
}

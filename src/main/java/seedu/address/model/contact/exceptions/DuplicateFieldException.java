package seedu.address.model.contact.exceptions;

/**
 * Signals that the operation will result in duplicate fields that are in conflict with other Contacts in
 * UniqueContactList
 */
public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException() {
        super("Operation would result in adding a field that conflicts with another contact");
    }

    public DuplicateFieldException(String field) {
        super("Operation would result in adding a field that conflicts with another contact: " + field);
    }
}

package seedu.ddd.model.contact.exceptions;

import seedu.ddd.model.contact.common.Id;

/**
 * Signals that the operation will result in duplicate Contacts
 * (Contacts are considered duplicates if they have the same identity).
 */
public class DuplicateContactException extends RuntimeException {

    public DuplicateContactException() {
        super("Operation would result in duplicate contacts");
    }

    public DuplicateContactException(Id id) {
        super(String.format("A contact with an ID of %1$s already exists", id));
    }

}

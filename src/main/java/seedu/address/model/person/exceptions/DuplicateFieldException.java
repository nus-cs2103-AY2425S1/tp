package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate fields that are in conflict with other Persons in
 * UniquePersonList
 */
public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException() {
        super("Operation would result in adding a field that conflicts with another person");
    }

    public DuplicateFieldException(String field) {
        super("Operation would result in adding a field that conflicts with another person: " + field);
    }
}

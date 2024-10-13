package seedu.address.model.person.exceptions;

public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException() {
        super("Operation would result in adding a field that conflicts with another person");
    }

    public DuplicateFieldException(String field) {
        super("Operation would result in adding a field that conflicts with another person: " + field);
    }
}

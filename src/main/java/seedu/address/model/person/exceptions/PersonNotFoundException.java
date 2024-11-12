package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class PersonNotFoundException extends RuntimeException {
    public final String personName;

    public PersonNotFoundException(String personName) {
        this.personName = personName;
    }
}

package seedu.address.model.person.exceptions;

import seedu.address.model.person.Person;

/**
 * Signals that a Person object of an unsupported subtype was encountered.
 */
public class IllegalPersonTypeException extends RuntimeException {
    public IllegalPersonTypeException(Person person) {
        super("Illegal person type found: " + person);
    }

}

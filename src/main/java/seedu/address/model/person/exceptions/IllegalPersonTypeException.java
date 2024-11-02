package seedu.address.model.person.exceptions;

import seedu.address.model.person.Person;

public class IllegalPersonTypeException extends RuntimeException {
    public IllegalPersonTypeException(Person person) {
        super("Illegal person type found: " + person);
    }

}

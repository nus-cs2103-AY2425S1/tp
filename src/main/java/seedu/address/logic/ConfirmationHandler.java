package seedu.address.logic;

import seedu.address.model.person.Person;

/**
 * Functional interface for testing purposes
 */
@FunctionalInterface
public interface ConfirmationHandler {
    boolean confirm(Person person);
}

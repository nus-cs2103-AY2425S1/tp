package seedu.address.model.wedding;

import seedu.address.model.person.Person;

/**
 * Represents the Wife in a Wedding.
 */
public class Wife extends Partner {

    /**
     * Constructs a {@code Wife}.
     */
    public Wife(String name) {
        super(name);
    }
    public Wife(String name, Person person) {
        super(name, person);
    }
}

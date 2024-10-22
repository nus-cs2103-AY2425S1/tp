package seedu.address.model.wedding;

import seedu.address.model.person.Person;

/**
 * Represents the Husband in a Wedding.
 */
public class Husband extends Partner {

    /**
     * Constructs a {@code Husband}.
     */
    public Husband(String name) {
        super(name);
    }
    public Husband(String name, Person person) {
        super(name, person);
    }
}

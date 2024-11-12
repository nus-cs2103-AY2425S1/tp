package seedu.address.model.employee;

import seedu.address.model.name.Name;

/**
 * Represents an employees's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EmployeeName extends Name {

    /**
     * Constructs a {@code EmployeeName}.
     *
     * @param name A valid name.
     */
    public EmployeeName(String name) {
        super(name);
    }

}

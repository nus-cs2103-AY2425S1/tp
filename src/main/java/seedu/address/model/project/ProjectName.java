package seedu.address.model.project;

import seedu.address.model.name.Name;

/**
 * Represents a Project's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ProjectName extends Name {

    /**
     * Constructs a {@code ProjectName}.
     *
     * @param name A valid name.
     */
    public ProjectName(String name) {
        super(name);
    }

}

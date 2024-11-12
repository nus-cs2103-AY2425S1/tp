package seedu.address.model.project;

import seedu.address.model.id.Id;

/**
 * Represents a Project's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ProjectId extends Id {

    /**
     * Constructs a {@code Id}.
     *
     * @param id A valid Id.
     */
    public ProjectId(String id) {
        super(id);
    }

}

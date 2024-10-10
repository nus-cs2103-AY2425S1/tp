package seedu.address.model;

import seedu.address.model.person.UniquePersonList;

/**
 * A group of persons which has an identifying name, and can be made and edited by the user.
 * Uses the implementation of UniquePersonList
 *
 * @see UniquePersonList
 */
public class Group extends UniquePersonList {
    private String name;

    /**
     * Creates a group named {@code name}.
     */
    public Group(String name) {
        super();
        this.name = name;
    }

    /**
     * Renames the group to the specified {@code newName}.
     */
    public void rename(String newName) {
        name = newName;
    }
}

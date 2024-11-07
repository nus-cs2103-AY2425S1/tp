package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * A group of persons which has an identifying name, and can be made and edited by the user.
 * Uses the implementation of UniquePersonList
 *
 * @see UniquePersonList
 */
public class Group extends UniquePersonList {
    private final String name;

    /**
     * Creates a group named {@code name}.
     */
    public Group(String name) {
        super();
        this.name = name;
    }

    /**
     * Creates a group with the same {@code name} and people as the existing group.
     *
     * @param existingGroup the existing group to be copied.
     */
    public Group(Group existingGroup) {
        super();
        requireNonNull(existingGroup);
        this.name = existingGroup.name;
        this.setPersons(existingGroup.asUnmodifiableObservableList());
    }

    /**
     * @return Name of the group.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if {@code group} has the same name as this group.
     * @return True if the groups have the same name, false otherwise.
     */
    public boolean isSameName(Group group) {
        requireNonNull(group);
        return name.equalsIgnoreCase(group.name);
    }

    /**
     * Returns the number of persons in the group.
     */
    public int size() {
        return asUnmodifiableObservableList().size();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Group otherGroup)) {
            return false;
        }
        return super.equals(o) && name.equals(otherGroup.name);
    }
}

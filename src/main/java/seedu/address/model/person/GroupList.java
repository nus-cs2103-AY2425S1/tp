package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.person.exceptions.DuplicateGroupException;
import seedu.address.model.person.exceptions.GroupNotFoundException;

/**
 * A list of {@code Group}s. This list does not accept groups with the same name.
 *
 * @see Group
 */
public class GroupList {
    private ArrayList<Group> groups;

    /**
     * Initialise {@code GroupList} with an empty list of groups.
     */
    public GroupList() {
        groups = new ArrayList<>();
    }

    /**
     * Checks if the list contains a group with the same name as {@code group}.
     * @return True if the list already contains a group with the same name, false otherwise.
     */
    public boolean contains(Group group) {
        requireNonNull(group);
        return groups.stream().anyMatch(group::sameName);
    }

    /**
     * Adds a new group to the list.
     * @throws DuplicateGroupException If {@code group} already exists (name has been used).
     */
    public void add(Group toAdd) throws DuplicateGroupException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        groups.add(toAdd);
    }

    /**
     * Removes the specified group from the list.
     * @throws GroupNotFoundException If the group does not exist.
     */
    public void remove(Group toRemove) throws GroupNotFoundException {
        requireNonNull(toRemove);
        for (Group group : groups) {
            if (group.sameName(toRemove)) {
                groups.remove(group);
                return;
            }
        }
        throw new GroupNotFoundException();
    }
}

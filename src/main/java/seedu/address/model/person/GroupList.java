package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.exceptions.DuplicateGroupException;
import seedu.address.model.person.exceptions.GroupNotFoundException;
import seedu.address.storage.JsonAdaptedGroup;

/**
 * A list of {@code Group}s. This list does not accept groups with the same name.
 *
 * @see Group
 */
public class GroupList {
    private final ArrayList<Group> groups;

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
     * Returns true if the list contains {@code group}.
     */
    public boolean containsExact(Group group) {
        requireNonNull(group);
        return groups.stream().anyMatch(group::equals);
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

    /**
     * Removes the group from the list which has the specified name.
     * @throws GroupNotFoundException If a group with the name does not exist.
     */
    public void remove(String groupName) throws GroupNotFoundException {
        requireNonNull(groupName);
        Group toRemove = new Group(groupName);
        remove(toRemove);
    }

    /**
     * Returns the group in the list which has name matching {@code groupName}.
     * @throws GroupNotFoundException If no such group exists.
     */
    public Group get(String groupName) throws GroupNotFoundException {
        requireNonNull(groupName);
        Group toFind = new Group(groupName);
        for (Group group : groups) {
            if (group.sameName(toFind)) {
                return group;
            }
        }
        throw new GroupNotFoundException();
    }

    /**
     * Returns the names of every {@code Group} in the {@code GroupList}.
     */
    public String getNames() {
        StringBuilder names = new StringBuilder();
        for (Group group : groups) {
            names.append(group.getName());
            names.append(", ");
        }
        // delete the last ", "
        names.deleteCharAt(names.length() - 1);
        names.deleteCharAt(names.length() - 1);
        return names.toString();
    }

    /**
     * Returns this list as a list of {@code JsonAdaptedGroup}s suitable for storage.
     */
    public List<JsonAdaptedGroup> toJson() {
        return groups.stream().map(JsonAdaptedGroup::new).toList();
    }

    /**
     * Clears the {@code GroupList} and sets its contents to that of {@code toCopy}.
     */
    public void set(GroupList toCopy) {
        groups.clear();
        groups.addAll(toCopy.groups);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroupList groupList)) {
            return false;
        }
        return groups.stream().allMatch(groupList::containsExact);
    }
}

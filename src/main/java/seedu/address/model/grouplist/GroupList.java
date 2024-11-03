package seedu.address.model.grouplist;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import seedu.address.model.group.Group;

/**
 * Represents a list of groups.
 * <p>
 * This class provides methods to manage and operate on a collection of {@link Group} objects.
 * </p>
 */
public class GroupList implements Iterable<Group> {
    private final Set<Group> groups;
    private boolean isModifiable;

    /**
     * Constructs an empty GroupList that is modifiable.
     */
    public GroupList() {
        this.groups = new HashSet<>();
        this.isModifiable = true;
    }
    /**
     * Constructs a GroupList with the specified set of groups.
     *
     * @param groups A set of groups to initialize the GroupList.
     */
    public GroupList(Set<Group> groups) {
        this.groups = new HashSet<>(groups);
        this.isModifiable = true;
    }

    /**
     * Adds a group to the GroupList if it is modifiable and not already present.
     *
     * @param group The group to add.
     * @throws UnsupportedOperationException if the GroupList is not modifiable.
     */
    public void add(Group group) {
        if (!isModifiable) {
            throw new UnsupportedOperationException("GroupList is not modifiable");
        }
        boolean isAdded = groups.stream().noneMatch(existing -> existing.toString().equalsIgnoreCase(group.toString()));
        if (isAdded) {
            groups.add(group);
        }
    }

    /**
     * Removes a group at the specified index from the GroupList if it is modifiable.
     *
     * @param index The index of the group to remove.
     * @throws UnsupportedOperationException if the GroupList is not modifiable.
     */
    public void remove(int index) {
        if (!isModifiable) {
            throw new UnsupportedOperationException("GroupList is not modifiable");
        }
        groups.remove(index);
    }
    public boolean isEmpty() {
        return this.groups.isEmpty();
    }
    public void addAll(GroupList grouplist) {
        this.groups.addAll(grouplist.getGroups());
    }
    @Override
    public Iterator<Group> iterator() {
        return groups.iterator();
    }
    /**
     * Marks the GroupList as unmodifiable.
     *
     * @return This GroupList instance.
     */
    public GroupList makeUnmodifiable() {
        this.isModifiable = false;
        return this;
    }
    public Set<Group> getGroups() {
        return groups;
    }
    public GroupList makeCopy() {
        return new GroupList(this.groups);
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GroupList)) {
            return false;
        }
        GroupList that = (GroupList) other;
        return this.groups.equals(that.groups);
    }

    @Override
    public int hashCode() {
        return groups.hashCode();
    }
}

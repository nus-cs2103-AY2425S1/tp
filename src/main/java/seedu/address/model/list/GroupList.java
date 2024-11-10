package seedu.address.model.list;

import java.util.Collections;
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
        assert groups != null : "groups in GroupList should not be null";
        this.groups = new HashSet<>(groups);
        this.isModifiable = true;
    }

    /**
     * Adds a group to the GroupList if the GroupList is modifiable and the group is not already present.
     *
     * @param group The group to add.
     * @throws UnsupportedOperationException if the GroupList is not modifiable.
     */
    public void addGroup(Group group) {
        if (!isModifiable) {
            throw new UnsupportedOperationException("GroupList is not modifiable");
        }
        // Trim and replace multiple spaces with a single space
        String normalizedGroupName = group.getGroupName().trim().replaceAll("\\s+", " ");

        boolean isAdded = groups.stream().noneMatch(existing -> {
            // Normalize existing group names as well before comparison
            String normalizedExistingGroupName = existing.getGroupName().trim().replaceAll("\\s+", " ");

            return normalizedExistingGroupName.equalsIgnoreCase(normalizedGroupName);
        });

        if (isAdded) {
            groups.add(group);
        }
    }

    /**
     * Checks if the GroupList is empty
     * @return Boolean which indicates if the list is empty
     */
    public boolean isEmpty() {
        return this.groups.isEmpty();
    }

    /**
     * Adds all groups from another GroupList to the current GroupList if it is modifiable.
     *
     * @param groupList The list of groups one would like to add to current list.
     * @throws UnsupportedOperationException if the GroupList is not modifiable.
     */
    public void addAll(GroupList groupList) {
        if (!isModifiable) {
            throw new UnsupportedOperationException("GroupList is not modifiable");
        }
        this.groups.addAll(groupList.getUnmodifiableGroups());
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
    public GroupList makeListUnmodifiable() {
        this.isModifiable = false;
        return this;
    }

    /**
     * Returns unmodifiable set of groups
     * @return This unmodifiable group set
     */
    public Set<Group> getUnmodifiableGroups() {
        return Collections.unmodifiableSet(groups);
    }

    public GroupList makeCopy() {
        return new GroupList(new HashSet<>(this.groups));
    }

    public int size() {
        return this.groups.size();
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

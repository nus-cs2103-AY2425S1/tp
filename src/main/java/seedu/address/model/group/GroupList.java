package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * A list of groups that enforces uniqueness between its elements and does not
 * allow nulls.
 * A group is considered unique by comparing using
 * {@code Group#isSameGroup(Group)}. As such, adding and updating of
 * groups uses {@code Group#isSameGroup(Group)} for equality to ensure that the
 * group being added or updated is
 * unique in terms of identity within the `GroupList`. However, the removal of a
 * group uses `Group#equals(Object)` to
 * ensure that the group with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Group#isSameGroup(Group)
 */
public class GroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    private final ObservableList<Group> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGroup);
    }

    /**
     * Adds a Group to the list.
     * The Group must not already exist in the list.
     */
    public void add(Group toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the list.
     * The group identity of {@code editedGroup} must not be the same as another
     * existing group in the list.
     *
     * @param target      The group to replace.
     * @param editedGroup The group to replace with.
     */
    public void setGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }
        if (!target.isSameGroup(editedGroup) && contains(editedGroup)) {
            throw new DuplicateGroupException();
        }
        internalList.set(index, editedGroup);
    }

    /**
     * Removes the equivalent Group from the list.
     * The Group must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    public void setGroups(GroupList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Groups}.
     * {@code Groups} must not contain duplicate Groups.
     */
    public void setGroups(List<Group> groups) {
        requireAllNonNull(groups);
        if (!groupsAreUnique(groups)) {
            throw new DuplicateGroupException();
        }

        internalList.setAll(groups);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupList)) {
            return false;
        }

        GroupList otherUniqueGroupList = (GroupList) other;
        return internalList.equals(otherUniqueGroupList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code Groups} contains only unique Groups.
     */
    private boolean groupsAreUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).isSameGroup(groups.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

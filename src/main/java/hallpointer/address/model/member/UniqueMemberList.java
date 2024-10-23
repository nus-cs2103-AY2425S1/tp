package hallpointer.address.model.member;

import static hallpointer.address.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import hallpointer.address.model.member.exceptions.DuplicateMemberException;
import hallpointer.address.model.member.exceptions.MemberNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of members that enforces uniqueness between its elements and does not allow nulls.
 * A member is considered unique by comparing using {@code Member#isSameMember(Member)}. As such, adding and updating of
 * members uses Member#isSameMember(Member) for equality so as to ensure that the member being added or updated is
 * unique in terms of identity in the UniqueMemberList. However, the removal of a member uses Member#equals(Object) so
 * as to ensure that the member with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Member#isSameMember(Member)
 */
public class UniqueMemberList implements Iterable<Member> {

    private final ObservableList<Member> internalList = FXCollections.observableArrayList();
    private final ObservableList<Member> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent member as the given argument.
     */
    public boolean contains(Member toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMember);
    }

    /**
     * Adds a member to the list.
     * The member must not already exist in the list.
     */
    public void add(Member toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMemberException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the member {@code target} in the list with {@code updatedMember}.
     * {@code target} must exist in the list.
     * The member identity of {@code updatedMember} must not be the same as another existing member in the list.
     */
    public void setMember(Member target, Member updatedMember) {
        requireAllNonNull(target, updatedMember);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MemberNotFoundException();
        }

        if (!target.isSameMember(updatedMember) && contains(updatedMember)) {
            throw new DuplicateMemberException();
        }

        internalList.set(index, updatedMember);
    }

    /**
     * Removes the equivalent member from the list.
     * The member must exist in the list.
     */
    public void remove(Member toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MemberNotFoundException();
        }
    }

    public void setMembers(UniqueMemberList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code members}.
     * {@code members} must not contain duplicate members.
     */
    public void setMembers(List<Member> members) {
        requireAllNonNull(members);
        if (!membersAreUnique(members)) {
            throw new DuplicateMemberException();
        }

        internalList.setAll(members);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Member> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Member> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueMemberList)) {
            return false;
        }

        UniqueMemberList otherUniqueMemberList = (UniqueMemberList) other;
        return internalList.equals(otherUniqueMemberList.internalList);
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
     * Returns true if {@code members} contains only unique members.
     */
    private boolean membersAreUnique(List<Member> members) {
        for (int i = 0; i < members.size() - 1; i++) {
            for (int j = i + 1; j < members.size(); j++) {
                if (members.get(i).isSameMember(members.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

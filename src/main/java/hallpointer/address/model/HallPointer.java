package hallpointer.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the hall-pointer level
 * Duplicates are not allowed (by .isSameMember comparison)
 */
@SuppressWarnings("checkstyle:Regexp")
public class HallPointer implements ReadOnlyHallPointer {

    private final UniqueMemberList members;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        members = new UniqueMemberList();
    }

    public HallPointer() {
    }

    /**
     * Creates an HallPointer using the Members in the {@code toBeCopied}
     */
    public HallPointer(ReadOnlyHallPointer toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the member list with {@code members}.
     * {@code members} must not contain duplicate members.
     */
    public void setMembers(List<Member> members) {
        this.members.setMembers(members);
    }
    /**
     * Resets the existing data of this {@code HallPointer} with {@code newData}.
     */
    public void resetData(ReadOnlyHallPointer newData) {
        requireNonNull(newData);

        setMembers(newData.getMemberList());
    }

    //// member-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in the hall pointer.
     */
    public boolean hasMembers(Member member) {
        requireNonNull(member);
        return members.contains(member);
    }

    /**
     * Adds a member to the hall pointer.
     * The member must not already exist in the hall pointer.
     */
    public void addMember(Member p) {
        members.add(p);
    }

    /**
     * Replaces the given member {@code target} in the list with {@code updatedMember}.
     * {@code target} must exist in the hall pointer.
     * The identity of {@code updatedMember} must not be the same as another existing member in the hall pointer.
     */
    public void setMember(Member target, Member updatedMember) {
        requireNonNull(updatedMember);

        members.setMember(target, updatedMember);
    }

    /**
     * Removes {@code member} from this {@code HallPointer}.
     * {@code member} must exist in the hall pointer.
     */
    public void removeMember(Member member) {
        members.remove(member);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("members", members)
                .toString();
    }

    @Override
    public ObservableList<Member> getMemberList() {
        return members.asUnmodifiableObservableList();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HallPointer)) {
            return false;
        }

        HallPointer otherHallPointer = (HallPointer) other;
        return members.equals(otherHallPointer.members);
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }
}

package hallpointer.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameMember comparison)
 */
@SuppressWarnings("checkstyle:Regexp")
public class AddressBook implements ReadOnlyAddressBook {

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

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Members in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setMembers(newData.getMemberList());
    }

    //// member-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in the address book.
     */
    public boolean hasMembers(Member member) {
        requireNonNull(member);
        return members.contains(member);
    }

    /**
     * Adds a member to the address book.
     * The member must not already exist in the address book.
     */
    public void addMember(Member p) {
        members.add(p);
    }

    /**
     * Replaces the given member {@code target} in the list with {@code updatedMember}.
     * {@code target} must exist in the address book.
     * The member identity of {@code updatedMember} must not be the same as another existing member in the address book.
     */
    public void setMember(Member target, Member updatedMember) {
        requireNonNull(updatedMember);

        members.setMember(target, updatedMember);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeMember(Member key) {
        members.remove(key);
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
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return members.equals(otherAddressBook.members);
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }
}

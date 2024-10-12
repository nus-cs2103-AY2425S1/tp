package hallpointer.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.UniqueSessionList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameMember comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueMemberList members;
    private final UniqueSessionList sessions;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        members = new UniqueMemberList();
        sessions = new UniqueSessionList();
    }

    public AddressBook() {}

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
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code target} must exist in the address book.
     * The member identity of {@code editedMember} must not be the same as another existing member in the address book.
     */
    public void setMember(Member target, Member editedMember) {
        requireNonNull(editedMember);

        members.setMember(target, editedMember);
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

    /**
     * Returns true if a session with the same identity as {@code session} exists in the address book.
     *
     * @param session The session to check.
     * @return True if the session exists, false otherwise.
     */
    //=========== Session ================================================================================
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return sessions.contains(session);
    }

    /**
     * Adds the given session to the address book.
     * {@code session} must not already exist in the address book.
     *
     * @param session The session to add.
     */
    public void addSession(Session session) {
        requireNonNull(session);
        sessions.add(session);
    }

    /**
     * Deletes the given session from the address book.
     * {@code session} must exist in the address book.
     *
     * @param session The session to delete.
     */
    public void deleteSession(Session session) {
        requireNonNull(session);
        sessions.remove(session);
    }

    /**
     * Replaces the given session {@code target} with {@code editedSession}.
     * {@code target} must exist in the address book.
     * The session identity of {@code editedSession} must not be the same as another
     * existing session in the address book.
     *
     * @param target The session to replace.
     * @param editedSession The new session.
     */
    public void setSession(Session target, Session editedSession) {
        requireNonNull(target);
        requireNonNull(editedSession);
        sessions.setSession(target, editedSession);
    }

    /**
     * Returns an unmodifiable view of the sessions list.
     *
     * @return The unmodifiable list of sessions.
     */
    public ObservableList<Session> getSessionList() {
        return sessions.asUnmodifiableObservableList();
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

package hallpointer.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import hallpointer.address.commons.core.GuiSettings;
import hallpointer.address.model.member.Member;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Member> PREDICATE_SHOW_ALL_MEMBERS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to false
     */
    Predicate<Member> PREDICATE_SHOW_NO_MEMBERS = unused -> false;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a member with the same identity as {@code member} exists in the address book.
     */
    boolean hasMember(Member member);

    /**
     * Deletes the given member.
     * The member must exist in the address book.
     */
    void deleteMember(Member target);
    /**
     * Adds the given member.
     * {@code member} must not already exist in the address book.
     */
    void addMember(Member member);

    /**
     * Replaces the given member {@code target} with {@code updatedMember}.
     * {@code target} must exist in the address book.
     * The member identity of {@code updatedMember} must not be the same as another existing member in the address book.
     */
    void setMember(Member target, Member updatedMember);

    /** Returns an unmodifiable view of the filtered member list */
    ObservableList<Member> getFilteredMemberList();

    /**
     * Updates the filter of the filtered member list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemberList(Predicate<Member> predicate);
}

package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

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
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Check if any of the already exist for the person.
     * {@code person} must already exist in the address book.
     */
    boolean tagExists(Person target, Set<Tag> tags);

    /**
     * Adds the tags to the specified person.
     * {@code person} must already exist in the address book.
     */
    void addTag(Person target, Set<Tag> tags);

    /**
     * Deletes the tags of the specified person.
     * {@code person} must already exist in the address book.
     * {@code tags} must already exist for the person.
     */
    void deleteTag(Person target, Set<Tag> tags);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds the group to the model.
     *
     * @throws NullPointerException if {@code group} is null.
     */
    void addGroup(Group group);

    void deleteGroup(Group groupToDelete);

    String groupsString();

    boolean hasGroupName(Group group);

    void updateFilteredGroupList(Predicate<Group> groupName);


    /**
     * Returns an unmodifiable view of the groups list
     */
    ObservableList<Group> getFilteredGroupList();
}

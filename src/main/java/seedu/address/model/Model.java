package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingNameContainsKeywordsPredicate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Wedding> PREDICATE_SHOW_ALL_WEDDINGS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

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
     * Returns the user prefs' wedding book file path.
     */
    Path getWeddingBookFilePath();

    /**
     * Sets the user prefs' wedding book file path.
     */
    void setWeddingBookFilePath(Path weddingBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same name as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same exact identity as {@code person} exists in the address book.
     */
    boolean hasExactPerson(Person person);

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

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Updates the filter of the filtered person list to filter by the given {@code JobContainsKeywordsPredicate}. */
    void updateFilteredPersonList(JobContainsKeywordsPredicate predicate);

    /**
     * Replaces wedding book data with the data in {@code weddingBook}.
     */
    void setWeddingBook(ReadOnlyWeddingBook weddingBook);

    /** Returns the WeddingBook */
    ReadOnlyWeddingBook getWeddingBook();

    /**
     * Returns true if a wedding with the same identity as {@code wedding} exists in the wedding book.
     */
    boolean hasWedding(Wedding wedding);

    /**
     * Deletes the given wedding.
     * The wedding must exist in the wedding book.
     */
    void deleteWedding(Wedding target);

    /**
     * Adds the given wedding.
     * {@code wedding} must not already exist in the wedding book.
     */
    void addWedding(Wedding wedding) throws CommandException;

    /**
     * Replaces the given wedding {@code target} with {@code editedWedding}.
     * {@code target} must exist in the wedding book.
     * The identity of {@code editedWedding} must not be the same as another existing wedding in the wedding book.
     */
    void setWedding(Wedding target, Wedding editedWedding);

    /** Returns an unmodifiable view of the filtered wedding list */
    ObservableList<Wedding> getFilteredWeddingList();

    /**
     * Updates the filter of the filtered wedding list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWeddingList(Predicate<Wedding> predicate);

    /** Updates the filter of the filtered wedding list to filter by the given {@code JobContainsKeywordsPredicate}. */
    void updateFilteredWeddingList(WeddingNameContainsKeywordsPredicate predicate);

    /**
     * Sets the person being tagged as a participant in the wedding that matches the tag.
     *
     * @param editedPerson Person whose tags have been added to them.
     * @param personToEdit Person who has tags currently being added to them.
     */
    void setPersonInWedding(Person editedPerson, Person personToEdit);

    /**
     * Updates the rest of the list of weddings with the editedPerson.
     *
     * @param personToEdit Person whose details are currently being edited.
     * @param editedPerson Person whose details have been edited.
     */
    void updatePersonInWedding(Person personToEdit, Person editedPerson);

    /**
     * Removes all tags from a person and removes the person from any weddings related to those tags.
     *
     * @param personToDelete the person whose tags will be removed.
     */
    Person personWithAllTagsRemoved(Person personToDelete);

    /**
     * Removes the person from the participant list of weddings that correspond to the specified tag(s).
     *
     * @param editedPerson Person whose specified tags have been deleted from.
     * @param tagsInBoth   Set of tags that exist as a wedding as well.
     */
    void deletePersonInWedding(Person editedPerson, Set<Tag> tagsInBoth);

    /**
     * Gets a list of weddings whose name matches that of the tags in the set.
     *
     * @param tags  Set of tags input by the user.
     * @return List of weddings that match the tag.
     */
    List<Wedding> getWeddingFromTags(Set<Tag> tags);
}

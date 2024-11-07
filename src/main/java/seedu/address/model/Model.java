package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.Command;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

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
     * Adds the given person at the specified index.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(int index, Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the full person list */
    ObservableList<Person> getFullPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException If {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds a tag to the tag list.
     *
     * @param tag The tag to be added.
     * @return True if the tag was successfully added, false if the tag already exists.
     */
    boolean addTag(Tag tag);

    /**
     * Adds a variable number of tags to the tag list.
     *
     * @param tags The tags to be added.
     * @return The set of tags which were added successfully (could be none).
     */
    Set<Tag> addTags(List<Tag> tags);

    /**
     * Deletes a tag from the tag list.
     *
     * @param tag The tag to be deleted.
     * @return True if the tag was successfully deleted, false if the tag does not exist.
     */
    boolean deleteTag(Tag tag);

    /**
     * Deletes a variable number of tags from the tag list.
     *
     * @param tags The tags to be deleted.
     * @return The set of tags which were deleted successfully (could be none).
     */
    Set<Tag> deleteTags(List<Tag> tags);

    /**
     * Renames a tag from the tag list.
     *
     * @param existingTag The tag to be renamed.
     * @param newTagName The new name of the tag after renaming.
     * @return True if the tag was successfully renamed.
     */
    boolean renameTag(Tag existingTag, String newTagName);

    /**
     * Checks if a tag exists in the tag list.
     *
     * @param tag The tag to check for existence.
     * @return True if the tag exists, false otherwise.
     */
    boolean hasTag(Tag tag);

    /**
     * Returns a set of tags that are in use by any of the persons in the address book.
     */
    Set<Tag> getTagsInUse();

    /**
     * Removes the deleted {@code Tag} from all persons in the address book.
     */
    Set<Person> removeTagFromPersons(Tag tag);

    /**
     * Edits the specified all persons in the address book with the tag.
     *
     * @param existingTag The existing tag to be renamed.
     * @param newTagName The new tag name after renaming.
     */
    public void editTagInPersons(Tag existingTag, String newTagName);

    /**
     * Checks if the size of the tag list is below or equal
     * to the maximum size acceptable, assuming an additional number of tags
     * are added to the existing tag list.
     *
     * @param additionalSize The number of tags to be added.
     * @return True if the size is acceptable, false otherwise.
     */
    boolean checkAcceptableTagListSize(int additionalSize);


    /**
     * Returns the ObservableList of the tag list
     * managed by this model.
     *
     * @return The {@code ObservableList} of the {@code TagList} instance.
     */
    ObservableList<Tag> getTagListAsObservableList();

    /**
     * Updates the tag list in the model.
     */
    void updateTagList();

    void updatePreviousCommand(Command nextCommand);

    Predicate<Person> getCurrentPredicate();

    Command getPreviousCommand();

    /**
     * Returns the tag filter set as an observable list.
     */
    ObservableList<Tag> getTagFiltersList();

    /**
     * Returns the tag filter set as an observable list.
     */
    ObservableList<RsvpStatus> getStatusFiltersList();

    /**
     * Adds tag filters to the model.
     */
    void addTagFilters(Set<Tag> tagFilters);

    /**
     * Adds RSVP status filters to the model.
     */
    void addStatusFilters(Set<RsvpStatus> statusFilters);

    /**
     * Checks whether the tag filter already exists in the list of filters.
     */
    boolean checkTagFilterAlreadyExists(Tag tagToCheck);

    /**
     * Checks whether the RSVP status filter already exists in the list of filters.
     */
    boolean checkStatusFilterAlreadyExists(RsvpStatus statusToCheck);

    /**
     * Removes the tag and RSVP status filters in the sets from the model.
     */
    void removeFilters(Set<Tag> tagFilters, Set<RsvpStatus> statusFilters);

    /**
     * Clears the filter set in the model.
     */
    void clearFilterSet();
}

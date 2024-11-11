package seedu.address.model;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;

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
    Path getCampusConnectFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setCampusConnectFilePath(Path campusConnectFilePath);

    /**
     * Replaces address book data with the data in {@code campusConnect}.
     */
    void setCampusConnect(ReadOnlyCampusConnect campusConnect);

    /** Returns the CampusConnect */
    ReadOnlyCampusConnect getCampusConnect();

    /**
     * Refreshes the current data in CampusConnect.
     */
    void refreshCampusConnect();

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
     * Deletes a tag from a person.
     * The person must exist as well as the tag.
     */
    void deletePersonTag(Person p, Tag tag);

    /**
     * Adds a set of tag to person.
     */
    void addPersonTags(Person p, Set<Tag>t);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Inserts person at the specific position.
     */
    void insertPerson(Person p, int ind);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns a list of tags currently defined in CampusConnect
     */
    ObservableList<Tag> getListOfCurrentTags();

    /**
     * Returns true if the specified {@code Tag} exists in CampusConnect
     */
    boolean containsTag(Tag tag);

    /**
     * Sets {@code TagCategory cat} to be the category of {@code Tag t}.
     */
    void setTagsCategory(Tag t, TagCategory cat);

    /**
     * Gets the recorded {@code TagCategory} of {@Tag t}.
     */
    TagCategory getTagCategory(Tag t);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Undoes the previous actions of users.
     */
    void undoCampusConnect() throws CommandException;

    /**
     * Restores state before previous undo actions of users.
     */
    void redoCampusConnect() throws CommandException;

    /**
     * Saves current state of model before execution.
     */
    void saveCurrentCampusConnect();

    /**
     * Undoes a state without saving current state when execution fails.
     */
    void undoExceptionalCommand() throws CommandException;
}

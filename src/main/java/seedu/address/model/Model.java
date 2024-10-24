package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Tag> PREDICATE_SHOW_ALL_TAGS = unused -> true;
    Predicate<Wedding> PREDICATE_SHOW_ALL_WEDDINGS = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

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
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    void setTag(Tag target, Tag editedTag);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code personPredicate}.
     * @throws NullPointerException if {@code personPredicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> personPredicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code tagPredicate}.
     * @throws NullPointerException if {@code tagPredicate} is null.
     */
    void updateFilteredPersonListByTag(Predicate<Tag> tagPredicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code weddingPredicate}.
     * @throws NullPointerException if {@code weddingPredicate} is null.
     */
    void updateFilteredPersonListByWedding(Predicate<Wedding> weddingPredicate);

    /**
     * Returns true if a tag with the same name as {@code toAdd} exists in the Wedlinker.
     */
    boolean hasTag(Tag toAdd);

    /**
     * Adds the given tag.
     * {@code person} must not already exist in the address book.
     */
    void addTag(Tag toAdd);

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    /** Returns an unmodifiable view of the filtered tag list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Deletes the given tag.
     * The tag must exist in the Wedlinker.
     */
    void deleteTag(Tag toDelete);

    /**
     * Returns true if a task with the same name as {@code toAdd} exists in the Wedlinker.
     */
    boolean hasTask(Task toAdd);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the address book.
     */
    void addTask(Task toAdd);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Replaces the given Task {@code target} with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing Task in the address book.
     */
    void setTask(Task target, Task editedTask);

    /**
     * Deletes the given task.
     * The task must exist in the Wedlinker.
     */
    void deleteTask(Task toDelete);

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns true if a wedding with the same name as {@code toAdd} exists in the Wedlinker.
     * @param toAdd A {@code Wedding} object, will be checked to see if the model already has this.
     * @return A boolean, true if the Wedlinker already contains the {@code Wedding}, false if it does not.
     */
    boolean hasWedding(Wedding toAdd);

    /**
     * Adds the given {@code Wedding} to the Wedlinker.
     * @param toAdd A {@code Wedding} to add to the Wedlinker.
     */
    void addWedding(Wedding toAdd);

    void setWedding(Wedding target, Wedding editedWedding);

    /**
     * Deletes the given wedding.
     * The wedding must exist in the Wedlinker.
     */
    void deleteWedding(Wedding toDelete);

    /** Returns an unmodifiable view of the filtered wedding list */
    ObservableList<Wedding> getFilteredWeddingList();

    /**
     * Updates the filter of the filtered wedding list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWeddingList(Predicate<Wedding> predicate);
}

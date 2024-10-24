package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Tag> filteredTags;
    private final FilteredList<Wedding> filteredWeddings;

    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTags = new FilteredList<>(this.addressBook.getTagList());
        filteredWeddings = new FilteredList<>(this.addressBook.getWeddingList());
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
        if (addressBook.hasVendor(target)) {
            addressBook.removeVendor(target);
        }
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasVendor(Person person) {
        requireNonNull(person);
        return addressBook.hasVendor(person);
    }

    @Override
    public void assignVendor(Person person) {
        requireNonNull(person);
        addressBook.addVendor(person);
    }

    @Override
    public void unassignVendor(Person person) {
        requireNonNull(person);
        addressBook.removeVendor(person);
    }


    @Override
    public void addTag(Tag tag) {
        addressBook.addTag(tag);
        updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
    }

    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return addressBook.hasTag(tag);
    }

    @Override
    public void setTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);
        addressBook.setTag(target, editedTag);
    }

    @Override
    public void deleteTag(Tag target) {
        addressBook.removeTag(target);
    }

    @Override
    public void addTask(Task task) {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }
    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return addressBook.hasTask(task);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        addressBook.setTask(target, editedTask);
    }

    @Override
    public void deleteTask(Task target) {
        addressBook.removeTask(target);
    }

    @Override
    public void addWedding(Wedding toAdd) {
        addressBook.addWedding(toAdd);
        updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
    }

    @Override
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireAllNonNull(target, editedWedding);
        addressBook.setWedding(target, editedWedding);
    }

    @Override
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return addressBook.hasWedding(wedding);
    }

    @Override
    public void deleteWedding(Wedding target) {
        addressBook.removeWedding(target);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPersonListByTag(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(person -> person.getTags().stream().anyMatch(predicate));
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return filteredTags;
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }
    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return filteredWeddings;
    }

    @Override
    public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPersonListByWedding(Predicate<Wedding> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(person -> person.getWeddings().stream().anyMatch(predicate));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredTags.equals(otherModelManager.filteredTags)
                && filteredTasks.equals(otherModelManager.filteredTasks)
                && filteredWeddings.equals(otherModelManager.filteredWeddings);
    }
}

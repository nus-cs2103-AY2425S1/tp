package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReminderManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static boolean isArchivedList = false;
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final BooleanProperty isUiArchived = new SimpleBooleanProperty(false);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final ArchivedAddressBook archivedAddressBook;
    private final FilteredList<Person> filteredArchivedPersons;
    private final FilteredList<Person> filteredPersons;
    private SortedList<Person> sortedPersons;
    private Comparator<Person> currentComparator = null;
    private final ReminderManager reminderManager;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyAddressBook archivedAddressBook) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.archivedAddressBook = new ArchivedAddressBook(archivedAddressBook);
        filteredArchivedPersons = new FilteredList<>(this.archivedAddressBook.getPersonList());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);
        reminderManager = new ReminderManager(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new ArchivedAddressBook());
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

    //=========== ArchivedAddressBook ========================================================================

    @Override
    public void addArchivedPerson(Person person) {
        archivedAddressBook.addArchivedPerson(person); // add but no update to filtered list
    }

    @Override
    public void deleteArchivedPerson(Person person) {
        archivedAddressBook.removeArchivedPerson(person); // add but no update to filtered list
    }

    @Override
    public ReadOnlyAddressBook getArchivedAddressBook() {
        return archivedAddressBook;
    }

    @Override
    public void setArchivedListMode(boolean isArchived) {
        isArchivedList = isArchived;
        isUiArchived.set(isArchived); // Update the property to notify UI
    }

    @Override
    public boolean hasArchivedPerson(Person person) {
        requireNonNull(person);
        return archivedAddressBook.hasArchivedPerson(person);
    }

    @Override
    public BooleanProperty isUiArchived() {
        return isUiArchived;
    }

    public boolean getIsArchivedList() {
        return isArchivedList;
    }

    public void setIsArchivedList(boolean value) {
        isArchivedList = value;
    }
    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        if (isArchivedList) {
            return filteredArchivedPersons;
        } else {
            return sortedPersons;
        }
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        if (isArchivedList) {
            filteredArchivedPersons.setPredicate(predicate);
        } else {
            filteredPersons.setPredicate(predicate); // Filter the list based on the predicate
        }
    }

    /**
     * Sorts the person list by deadline/name, whether it's filtered or full, then updates the address book
     */
    @Override
    public void sortByComparator(Comparator<Person> comparator) {
        currentComparator = comparator;

        // Initial size of the addressBook with all contacts
        int initialSize = addressBook.getPersonList().size();

        //Initial size of filtered persons i.e if a FindCommand has been executed
        int initialSortedSize = sortedPersons.size();

        sortedPersons.setComparator(comparator);

        SortedList<Person> sortedAllPersons = new SortedList<>(this.addressBook.getPersonList());
        sortedAllPersons.setComparator(comparator);

        // Assert that sizes remains same after sorting
        assert sortedAllPersons.size() == initialSize;
        assert sortedPersons.size() == initialSortedSize;

        addressBook.setPersons(sortedAllPersons);
    }

    //=========== Reminder Manager ==========================================================================

    @Override
    public ReminderManager getReminderManager() {
        return reminderManager;
    }

    @Override
    public StringProperty getCurrentReminderProperty() {
        return reminderManager.currentReminderProperty();
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
                && archivedAddressBook.equals(otherModelManager.archivedAddressBook)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredArchivedPersons.equals(otherModelManager.filteredArchivedPersons);
    }
}

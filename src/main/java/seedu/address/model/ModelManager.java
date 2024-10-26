package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.DateDistantToRecentComparator;
import seedu.address.model.person.DateRecentToDistantComparator;
import seedu.address.model.person.Person;
import seedu.address.model.person.PriorityHighToLowComparator;
import seedu.address.model.person.PriorityLowToHighComparator;
import seedu.address.model.person.Reminder;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final ReminderAddressBook reminderAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Reminder> filteredReminders;
    private final SortedList<Person> sortedPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyReminderAddressBook reminderAddressBook) {
        requireAllNonNull(addressBook, userPrefs, reminderAddressBook);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.reminderAddressBook = new ReminderAddressBook(reminderAddressBook);
        sortedPersons = new SortedList<>(this.addressBook.getPersonList());
        filteredPersons = new FilteredList<>(sortedPersons);
        filteredReminders = new FilteredList<>(this.reminderAddressBook.getReminderList());
        applySavedSortPreference();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new ReminderAddressBook());
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
    public void setSortPreference(String sortPreference) {
        requireNonNull(userPrefs);
        userPrefs.setSortPreference(sortPreference);
    }

    @Override
    public void applySavedSortPreference() {
        String sortPreference = userPrefs.getSortPreference();

        switch(sortPreference) {
        case "high":
            updateSortedPersonList(new PriorityHighToLowComparator());
            break;
        case "low":
            updateSortedPersonList(new PriorityLowToHighComparator());
            break;
        case "distant":
            updateSortedPersonList(new DateDistantToRecentComparator());
            break;
        case "recent":
            updateSortedPersonList(new DateRecentToDistantComparator());
            break;
        default:
            break;
        }
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

    @Override
    public Path getReminderAddressBookFilePath() {
        return userPrefs.getReminderAddressBookFilePath();
    }

    @Override
    public void setReminderAddressBookFilePath(Path reminderAddressBookFilePath) {
        requireNonNull(reminderAddressBookFilePath);
        userPrefs.setAddressBookFilePath(reminderAddressBookFilePath);
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

    @Override
    public void addReminder(Reminder reminder, Person person) {
        person.getReminderList().addReminder(reminder);
    }

    //=========== ReminderAddressBook =========================================================================

    @Override
    public void setReminderAddressBook(ReadOnlyReminderAddressBook reminderAddressBook) {
        this.reminderAddressBook.resetData(reminderAddressBook);
    }

    @Override
    public ReadOnlyReminderAddressBook getReminderAddressBook() {
        return reminderAddressBook;
    }

    @Override
    public void deleteReminderInBook(Reminder target) {
        reminderAddressBook.removeReminder(target);
    }

    @Override
    public void addReminderToBook(Reminder reminder) {
        reminderAddressBook.addReminder(reminder);
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
    public void updateSortedPersonList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        sortedPersons.setComparator(comparator);
    }

    //=========== Filtered Reminder List Accessors ===========================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reminder}
     */
    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return filteredReminders;
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
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}

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
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ClientHub clientHub;
    private final UserPrefs userPrefs;
    private final ObservableList<Person> originalPersons;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;
    private final ObservableList<Reminder> originalReminders;
    private final FilteredList<Reminder> filteredReminders;
    private final SortedList<Reminder> sortedReminders;


    /**
     * Initializes a ModelManager with the given clientHub and userPrefs.
     */
    public ModelManager(ReadOnlyClientHub clientHub, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(clientHub, userPrefs);

        logger.fine("Initializing with clienthub: " + clientHub + " and user prefs " + userPrefs);

        this.clientHub = new ClientHub(clientHub);
        this.userPrefs = new UserPrefs(userPrefs);
        this.originalPersons = this.clientHub.getPersonList();
        this.filteredPersons = new FilteredList<>(originalPersons);
        this.sortedPersons = new SortedList<>(filteredPersons);

        this.originalReminders = this.clientHub.getReminderList();
        this.filteredReminders = new FilteredList<>(originalReminders);
        this.sortedReminders = new SortedList<>(filteredReminders);

        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredReminderList();
    }

    public ModelManager() {
        this(new ClientHub(), new UserPrefs());
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
    public Path getClientHubFilePath() {
        return userPrefs.getClientHubFilePath();
    }

    @Override
    public void setClientHubFilePath(Path clientHubFilePath) {
        requireNonNull(clientHubFilePath);
        userPrefs.setClientHubFilePath(clientHubFilePath);
    }

    //=========== ClientHub ================================================================================

    @Override
    public void setClientHub(ReadOnlyClientHub clientHub) {
        this.clientHub.resetData(clientHub);
    }

    @Override
    public ReadOnlyClientHub getClientHub() {
        return clientHub;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return clientHub.hasPerson(person);
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return clientHub.hasReminder(reminder);
    }

    @Override
    public void deletePerson(Person target) {
        clientHub.removePerson(target);
    }

    @Override
    public void deleteReminder(Reminder target) {
        clientHub.removeReminder(target);
    }

    @Override
    public void addPerson(Person person) {
        clientHub.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addReminder(Reminder reminder) {
        clientHub.addReminder(reminder);
        updateFilteredReminderList();
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        clientHub.setPerson(target, editedPerson);
    }

    @Override
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireAllNonNull(target, editedReminder);

        clientHub.setReminder(target, editedReminder);
    }

    @Override
    public int getDisplayPersonsListSize() {
        return this.getDisplayPersons().size();
    }

    @Override
    public int getDisplayRemindersListSize() {
        return this.getDisplayReminders().size();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedClientHub}
     */
    @Override
    public ObservableList<Person> getDisplayPersons() {
        return sortedPersons;
    }

    @Override
    public ObservableList<Reminder> getDisplayReminders() {
        return sortedReminders;
    }

    //=========== Unfiltered Person List Accessors =============================================================

    @Override
    public void updateUnfilteredList() {
        // Reset the filter to show all persons
        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);

        // Remove any sorting in the list
        sortedPersons.setComparator(null);

        // Force resort to match original list order if needed
        sortedPersons.setComparator((p1, p2) -> 0); // Force a resort
        sortedPersons.setComparator(null); // Remove the comparator
    }

    //=========== Unfiltered Reminder List Accessors =============================================================

    @Override
    public void updateUnfilteredReminderList() {
        // Reset the filter to show all reminders
        filteredReminders.setPredicate(PREDICATE_SHOW_ALL_REMINDERS);
    }

    //=========== Filtered Person List Accessors =============================================================

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Reminder List Accessors =============================================================

    @Override
    public void updateFilteredReminderList() {
        filteredReminders.setPredicate(PREDICATE_SHOW_ALL_REMINDERS);

        Comparator<Reminder> reminderComparator = Comparator.comparing(Reminder::getDateTime);

        sortedReminders.setComparator(reminderComparator);
    }

    //=========== Sorted Person List Accessors =============================================================

    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        sortedPersons.setComparator(comparator);
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
        return clientHub.equals(otherModelManager.clientHub)
                && userPrefs.equals(otherModelManager.userPrefs)
                && originalPersons.equals(otherModelManager.originalPersons)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && sortedPersons.equals(otherModelManager.sortedPersons)
                && originalReminders.equals(otherModelManager.originalReminders)
                && filteredReminders.equals(otherModelManager.filteredReminders);
    }

}

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
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Meeting;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final ScheduleList scheduleList;
    private final FilteredList<Meeting> weeklySchedule;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(
            ReadOnlyAddressBook addressBook,
            ReadOnlyUserPrefs userPrefs,
            ReadOnlyScheduleList scheduleList) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: "
                + addressBook + " and user prefs "
                + userPrefs + "and stored schedule" + scheduleList);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.scheduleList = new ScheduleList(scheduleList);
        weeklySchedule = new FilteredList<>(this.scheduleList.getMeetingList());
    }

    /**
     * Constructs a {@code ModelManager} with the given {@code ReadOnlyAddressBook} and {@code ReadOnlyUserPrefs}.
     * Initializes the address book, user preferences, filtered list of persons, and an empty schedule list.
     *
     * @param addressBook The address book used to initialize the model manager. Cannot be null.
     * @param userPrefs   The user preferences used to initialize the model manager. Cannot be null.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.scheduleList = new ScheduleList();
        weeklySchedule = new FilteredList<>(this.scheduleList.getMeetingList());
    }


    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new ScheduleList());
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
    
    @Override
    public void favouritePerson(Person target) {
        requireAllNonNull(target);
        
        addressBook.favouritePerson(target);
    }

    //=========== ScheduleList ================================================================================
    @Override
    public void setScheduleList(ReadOnlyScheduleList scheduleList) {
        this.scheduleList.resetData(scheduleList);
    }

    @Override
    public ReadOnlyScheduleList getScheduleList() {
        return scheduleList;
    }

    @Override
    public void deleteMeeting(Meeting target) {
        scheduleList.removeMeeting(target);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        scheduleList.addMeeting(meeting);
        // updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireAllNonNull(target, editedMeeting);

        scheduleList.setMeeting(target, editedMeeting);
    }

    /**
     * Returns true if the given meeting conflicts with any existing meetings in the schedule list.
     *
     * @param newMeeting The meeting to check for conflicts.
     * @return True if a conflict exists, false otherwise.
     */
    @Override
    public boolean hasMeeting(Meeting newMeeting) {
        return scheduleList.hasMeeting(newMeeting);
    }

    @Override
    public boolean hasPersonInMeeting(Person person) {
        return scheduleList.hasPersonInMeeting(person);
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

    //=========== Weekly Meeting List Accessors =============================================================
    @Override
    public ObservableList<Meeting> getWeeklySchedule() {
        return weeklySchedule;
    }

    @Override
    public void changeWeeklySchedule(Predicate<Meeting> predicate) {
        requireNonNull(predicate);
        weeklySchedule.setPredicate(predicate);
    }
    @Override
    public ObservableList<Meeting> getCurrentWeeklySchedule(Predicate<Meeting> predicate) {
        requireNonNull(predicate);
        weeklySchedule.setPredicate(predicate);
        return weeklySchedule;
    }

    @Override
    public Meeting getMeeting(Index i) {
        return weeklySchedule.get(i.getZeroBased());
    }
}

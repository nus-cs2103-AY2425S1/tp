package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.DateUtil.getFilterDateString;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.AppointmentContainsDatePredicate;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Person> filteredAppointments;
    private final SortedList<Person> sortedAppointments;
    private AppointmentContainsDatePredicate filterPredicate;
    private final FilteredList<Person> allAppointmentsList;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredAppointments = new FilteredList<>(this.addressBook.getPersonList());
        allAppointmentsList = new FilteredList<>(this.addressBook.getPersonList());
        initializeAppointmentList();
        initializeAllAppointmentsList();
        sortedAppointments = new SortedList<>(filteredAppointments, Comparator.comparing(Person::getAppointmentStart));
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
    public String getFilteredAppointmentDate() {
        return getFilterDateString(filterPredicate.getDate());
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Appointment list methods =========================================================

    private void initializeAppointmentList() {
        AppointmentContainsDatePredicate predicate = new AppointmentContainsDatePredicate(LocalDate.now());
        filterPredicate = predicate;
        updateFilteredAppointmentList(predicate);
    }

    private void initializeAllAppointmentsList() {
        updateAllAppointmentsList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    /**
     * Returns an unmodifiable view of the list of sorted and filtered appointments backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getSortedAppointmentList() {
        return sortedAppointments;
    }

    @Override
    public void updateFilteredAppointmentList(AppointmentContainsDatePredicate predicate) {
        requireNonNull(predicate);
        filterPredicate = predicate;
        filteredAppointments.setPredicate(predicate);
    }

    @Override
    public ObservableList<Person> getAllAppointmentsList() {
        return allAppointmentsList;
    }

    @Override
    public void updateAllAppointmentsList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        allAppointmentsList.setPredicate(predicate);
    }

    @Override
    public boolean hasOverlappingAppointment(Person newAppointmentPerson) {
        LocalDateTime newStart = newAppointmentPerson.getAppointmentStart();
        LocalDateTime newEnd = newAppointmentPerson.getAppointmentEnd();

        for (Person person : allAppointmentsList) {
            LocalDateTime existingStart = person.getAppointmentStart();
            LocalDateTime existingEnd = person.getAppointmentEnd();

            boolean startsBeforeExistingEnds = newStart.isBefore(existingEnd);
            boolean endsAfterExistingStarts = newEnd.isAfter(existingStart);
            boolean startsAtSameTime = newStart.isEqual(existingStart);
            boolean endsAtSameTime = newEnd.isEqual(existingEnd);

            if ((startsBeforeExistingEnds && endsAfterExistingStarts) || startsAtSameTime || endsAtSameTime) {
                return true;
            }
        }
        return false;
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
                && filteredAppointments.equals(otherModelManager.filteredAppointments);
    }

}

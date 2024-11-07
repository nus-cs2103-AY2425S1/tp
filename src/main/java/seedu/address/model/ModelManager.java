package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;

    private final ObservableList<Appointment> appointments;
    private final FilteredList<Appointment> filteredAppointments;
    private final SortedList<Appointment> sortedAppointments;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, List<Appointment> appointments, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        sortedPersons = new SortedList<>(filteredPersons); // sortedPersons is updated along with filteredPersons
        sortedPersons.setComparator(Comparator.comparing(Person::getPriority) // sort by descending priority
                .thenComparing(person -> person.getName().toString())); // sort by name alphabetically after

        this.appointments = FXCollections.observableList(appointments);
        filteredAppointments = new FilteredList<>(this.appointments);
        sortedAppointments = new SortedList<>(filteredAppointments);
        sortedAppointments.setComparator(Comparator.comparing(Appointment::date)
                .thenComparing(Appointment::startTime));
    }

    public ModelManager() {
        this(new AddressBook(), new ArrayList<>(), new UserPrefs());
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
        return userPrefs.getSocialBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setSocialBookFilePath(addressBookFilePath);
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
        updateFilteredPersonList(PREDICATE_SHOW_CURRENT_PERSONS);
    }

    @Override
    public void addPerson(Person person, int index) {
        addressBook.addPerson(person, index);

        /*
            Since this method is currently only used for undoing delete command,
            the filtered person list does not need to be updated to show all current persons
         */
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    //=========== Appointments ================================================================================

    @Override
    public List<Appointment> getAppointmentList() {
        return FXCollections.unmodifiableObservableList(appointments);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        requireNonNull(appointment);
        appointments.add(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void addAppointment(int index, Appointment appointment) {
        requireNonNull(appointment);
        appointments.add(index, appointment);
    }

    @Override
    public void setAppointment(int index, Appointment appointment) {
        requireNonNull(appointment);
        appointments.set(index, appointment);
    }

    @Override
    public void updateAppointments(Name oldName, Name newName) {
        requireAllNonNull(oldName, newName);
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.name().equals(oldName)) {
                appointments.set(i, appointment.withName(newName));
            }
        }
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        requireNonNull(appointment);
        appointments.remove(appointment);
    }

    @Override
    public Appointment deleteAppointment(int index) {
        return appointments.remove(index);
    }

    @Override
    public List<Appointment> deleteAppointments(Name name) {
        requireNonNull(name);
        List<Appointment> appointmentsToDelete = appointments.stream()
                .filter(appointment -> appointment.name().equals(name))
                .toList();
        appointments.removeAll(appointmentsToDelete);
        return appointmentsToDelete;
    }

    @Override
    public List<Appointment> getConflictingAppointments(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.stream().filter(appointment::hasConflictWith).toList();
    }

    @Override
    public List<Appointment> getConflictingAppointments(Appointment oldAppointment, Appointment newAppointment) {
        requireAllNonNull(oldAppointment, newAppointment);
        return appointments.stream()
                .filter(appointment -> !appointment.equals(oldAppointment))
                .filter(newAppointment::hasConflictWith)
                .toList();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return sortedPersons; // sortedPersons wraps filteredPersons and sorts it, so just return sorted version
    }

    @Override
    public Predicate<? super Person> getFilteredPersonListPredicate() {
        Predicate<? super Person> predicate = filteredPersons.getPredicate();

        // predicate may be null, which means always true
        if (predicate == null) {
            return unused -> true;
        }

        return predicate;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateSortingOrder(Comparator<Person> comparator) {
        sortedPersons.setComparator(comparator);
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return sortedAppointments;
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager otherModelManager)) {
            return false;
        }

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredAppointments.equals(otherModelManager.filteredAppointments);
    }
}

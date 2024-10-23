package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final AppointmentManager appointmentManager;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.appointmentManager = new AppointmentManager(this);
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
        appointmentManager.update();
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        appointmentManager.update();
        addressBook.removePerson(target);
        // want to check for roles and remove the links between this person, and
        // the patients/ caregiver based on the role
        for (Nric patientnric : target.getPatients()) {
            addressBook.deleteLink(addressBook.getPerson(patientnric), target);
        }
        for (Nric caregivernric : target.getCaregivers()) {
            addressBook.deleteLink(target, addressBook.getPerson(caregivernric));
        }

    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        appointmentManager.update();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
        appointmentManager.update();
    }

    @Override
    public boolean hasLink(Person patient, Person caregiver) {
        return addressBook.hasLink(patient, caregiver);
    }

    @Override
    public void addLink(Person patient, Person caregiver) {
        addressBook.addLink(patient, caregiver);
    }

    @Override
    public void deleteLink(Person patient, Person caregiver) {
        addressBook.deleteLink(patient, caregiver);
    }

    @Override
    public Person getPerson(Nric nric) {
        return addressBook.getPerson(nric);
    }

    /**
     * Returns the list of unfiltered persons
     */
    @Override
    public ObservableList<Person> getUnfilteredPersonList() {
        return addressBook.getPersonList();
    }

    public AppointmentManager getAppointmentManager() {
        return appointmentManager;
    }

    /**
     * Adds a new appointment to the person's list of appointments if there are no conflicts with existing appointments.
     * If there is a conflict, an IllegalArgumentException is thrown.
     *
     * @param newAppointment The appointment to be added.
     * @param person The person to whom the appointment is added.
     */
    @Override
    public boolean addAppointment(Appointment newAppointment, Person person) {
        return appointmentManager.addAppointment(newAppointment, person);
    }

    /**
     * Removes an appointment from the person's list of appointments.
     *
     * @param appointment The appointment to be removed.
     * @param person The person from whom the appointment is removed.
     */
    @Override
    public void removeAppointment(Appointment appointment, Person person) {
        appointmentManager.removeAppointment(appointment, person);
    }

    /**
     * Edits an appointment from the person's list of appointments.
     *
     * @param appointment The appointment to be edited.
     * @param person The person from whom the appointment is edited.
     * @param newAppointment The new appointment to replace the old appointment.
     */
    @Override
    public void editAppointment(Appointment appointment, Person person, Appointment newAppointment) {
        appointmentManager.editAppointment(appointment, person, newAppointment);
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointmentManager.getAppointments());
    }

    public List<Appointment> getAppointmentsForPerson(Person person) {
        List<Appointment> temp = new ArrayList<>(person.getAppointments());
        temp.sort(Comparator.comparing(Appointment::getStartTime));
        return temp;
    }

    public Appointment getAppointmentForPersonAndTime(Person person, LocalDateTime startTime) {
        List<Appointment> temp = this.getAppointmentsForPerson(person);
        for (Appointment appointment : temp) {
            if (appointment.getStartTime().equals(startTime)) {
                return appointment;
            }
        }
        return null;
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        appointmentManager.update();
        return appointmentManager.hasAppointment(appointment);
    }

    public void addNoteToPerson(Note note, Person person) {
        addressBook.addNoteToPerson(note, person);
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

}

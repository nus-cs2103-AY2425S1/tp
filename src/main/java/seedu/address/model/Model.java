package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Person> PREDICATE_SHOW_ARCHIVED_PERSONS = Person::isArchived;
    Predicate<Person> PREDICATE_SHOW_CURRENT_PERSONS = PREDICATE_SHOW_ARCHIVED_PERSONS.negate();
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

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
     * Adds the given person at specified index.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person, int index);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the current predicate used by the filtered person list.
     * Should not return {@code null}.
     */
    Predicate<Person> getFilteredPersonListPredicate();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the order of the person list according to the given parameter.
     *
     * @param comparator Specifies new comparison criteria to order person list by.
     */
    void updateSortingOrder(Comparator<Person> comparator);

    /**
     * Replaces appointment list data with the data in {@code appointments}.
     */
    void setAppointmentList(List<Appointment> appointments);

    /** Returns an unmodifiable view of the appointment list */
    List<Appointment> getAppointmentList();

    /**
     * Adds the given appointment.
     * {@code appointment} must not conflict with any existing appointments.
     */
    void addAppointment(Appointment appointment);

    /**
     * Adds the given appointment at the specified index.
     * {@code appointment} must not conflict with any existing appointments.
     */
    void addAppointment(int index, Appointment appointment);

    /**
     * Replaces the appointment at the specified index with {@code appointment}.
     */
    void setAppointment(int index, Appointment appointment);

    /**
     * Updates all appointments with {@code oldName} to {@code newName}.
     */
    void updateAppointments(Name oldName, Name newName);

    /**
     * Deletes the given appointment.
     */
    void deleteAppointment(Appointment appointment);

    /**
     * Deletes the appointment at the specified index and returns the deleted appointment.
     */
    Appointment deleteAppointment(int index);

    /**
     * Deletes all appointments with the given name and returns the deleted appointments.
     */
    List<Appointment> deleteAppointments(Name name);

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    /**
     * Returns a list of appointments that conflict with the given appointment.
     */
    List<Appointment> getConflictingAppointments(Appointment appointment);

    /**
     * Returns a list of appointments that conflict with {@code newAppointment}, excluding {@code oldAppointment}.
     */
    List<Appointment> getConflictingAppointments(Appointment oldAppointment, Appointment newAppointment);
}

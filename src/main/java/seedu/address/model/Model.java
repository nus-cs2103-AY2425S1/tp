package seedu.address.model;

import java.nio.file.Path;
import java.util.TreeSet;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.filteredappointment.FilteredAppointment;
import seedu.address.model.patient.Patient;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Patient> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns true if a patient with the same identity as {@code patient} exists in the address book.
     */
    boolean hasPerson(Patient patient);

    /**
     * Deletes the given patient.
     * The patient must exist in the address book.
     */
    void deletePerson(Patient target);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the address book.
     */
    void addPerson(Patient patient);

    /**
     * Replaces the given patient {@code target} with {@code editedPatient}.
     * {@code target} must exist in the address book.
     * The patient identity of {@code editedPatient} must not be the same as another existing patient in the address book.
     */
    void setPerson(Patient target, Patient editedPatient);

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPersonList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Patient> predicate);

    void setFilteredAppts(TreeSet<FilteredAppointment> filteredAppointments);

    TreeSet<FilteredAppointment> getFilteredAppts();
}

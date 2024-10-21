package seedu.address.stubs.delete;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.attendance.AttendanceEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;


/**
 * Represents the stub in-memory model of the address book data.
 * Several methods return null
 * The purpose of this stub is to test null handling of the model's returned values
 */
public class ModelManagerStub implements Model {
    /** {@code Predicate} that always evaluate to true */
    private static final Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

    }

    /**
     * Returns the user prefs.
     */
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return null;
    }

    /**
     * Returns the user prefs' GUI settings.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return null;
    }

    /**
     * Sets the user prefs' GUI settings.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {

    }

    /**
     * Returns the user prefs' address book file path.
     */
    @Override
    public Path getAddressBookFilePath() {
        return null;
    }

    /**
     * Sets the user prefs' address book file path.
     */
    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {

    }

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {

    }

    /** Returns the AddressBook */
    public ReadOnlyAddressBook getAddressBook() {
        return null;
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    @Override
    public boolean hasPerson(Person person) {
        return false;
    }

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    @Override
    public void deletePerson(Person target) {

    }

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    @Override
    public void addPerson(Person person) {

    }

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    @Override
    public void setPerson(Person target, Person editedPerson) {

    }

    /** Returns an unmodifiable view of the filtered person list */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return null;
    }

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {

    }

    @Override
    public void addAttendanceEvent(AttendanceEvent event) {

    }

    @Override
    public boolean hasAttendanceEvent(AttendanceEvent event) {
        return false;
    }

    @Override
    public Optional<AttendanceEvent> getAttendanceEvent(String eventName) {
        return Optional.empty();
    }

    @Override
    public void markStudentAttendance(String eventName, StudentId studentId, boolean isPresent) {

    }

    @Override
    public ObservableList<AttendanceEvent> getAttendanceEventList() {
        return FXCollections.observableArrayList();
    }

    @Override
    public List<Person> getStudentsByAttendance(String eventName, boolean isPresent) {
        return Collections.emptyList();
    }

    @Override
    public void deleteAttendanceEvent(AttendanceEvent event) {

    }
}

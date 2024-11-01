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
     * Does nothing.
     */
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

    }

    /**
     * Returns a null user prefs.
     */
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return null;
    }

    /**
     * Returns a null user prefs' GUI settings.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return null;
    }

    /**
     * Does nothing.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {

    }

    /**
     * Returns a null user prefs' address book file path.
     */
    @Override
    public Path getAddressBookFilePath() {
        return null;
    }

    /**
     * Does nothing.
     */
    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {

    }

    /**
     * Does nothing.
     */
    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {

    }

    /** Returns a null AddressBook */
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
     * Does nothing.
     */
    @Override
    public void deletePerson(Person target) {

    }

    /**
     * Does nothing.
     */
    @Override
    public void addPerson(Person person) {

    }

    /**
     * Does nothing.
     */
    @Override
    public void setPerson(Person target, Person editedPerson) {

    }

    /** Returns a null unmodifiable view of the filtered person list */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return null;
    }

    /**
     * Does nothing.
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

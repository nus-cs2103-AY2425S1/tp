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
 * A stub implementation of the {@code Model} interface for testing purposes.
 * This stub simulates the behavior of the {@code Model} interface methods by returning null or performing no actions.
 * The main purpose of this class is to test how the system handles null values returned by model methods.
 *
 * Several methods return {@code null} or do nothing as they are not intended to modify state.
 */
public class ModelManagerStub implements Model {
    /** {@code Predicate} that always evaluate to true */
    private static final Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Does nothing. This method is a no-op for testing purposes.
     *
     * @param userPrefs The user preferences to be set.
     */
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

    }

    /**
     * Returns {@code null} as a stub for the user preferences.
     *
     * @return {@code null}.
     */
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return null;
    }

    /**
     * Returns {@code null} as a stub for the GUI settings in user preferences.
     *
     * @return {@code null}.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return null;
    }

    /**
     * Does nothing. This method is a no-op for testing purposes.
     *
     * @param guiSettings The GUI settings to be set.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        // No actions performed
    }

    /**
     * Returns {@code null} as a stub for the address book file path.
     *
     * @return {@code null}.
     */
    @Override
    public Path getAddressBookFilePath() {
        return null;
    }

    /**
     * Does nothing. This method is a no-op for testing purposes.
     *
     * @param addressBookFilePath The file path to be set.
     */
    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        // No actions performed
    }

    /**
     * Does nothing. This method is a no-op for testing purposes.
     *
     * @param addressBook The address book to be set.
     */
    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        // No actions performed
    }

    /**
     * Returns {@code null} as a stub for the address book.
     *
     * @return {@code null}.
     */
    public ReadOnlyAddressBook getAddressBook() {
        return null;
    }

    /**
     * Always returns {@code false} as this is a stub implementation.
     *
     * @param person The person to check.
     * @return {@code false}.
     */
    @Override
    public boolean hasPerson(Person person) {
        return false;
    }

    /**
     * Does nothing. This method is a no-op for testing purposes.
     *
     * @param target The person to delete.
     */
    @Override
    public void deletePerson(Person target) {
        // No actions performed
    }

    /**
     * Does nothing. This method is a no-op for testing purposes.
     *
     * @param person The person to add.
     */
    @Override
    public void addPerson(Person person) {
        // No actions performed
    }

    /**
     * Does nothing. This method is a no-op for testing purposes.
     *
     * @param target The person to update.
     * @param editedPerson The edited person.
     */
    @Override
    public void setPerson(Person target, Person editedPerson) {
        // No actions performed
    }

    /**
     * Returns {@code null} as a stub for the filtered person list.
     *
     * @return {@code null}.
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return null;
    }

    /**
     * Does nothing. This method is a no-op for testing purposes.
     *
     * @param predicate The predicate to update the filtered list.
     */
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        // No actions performed
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

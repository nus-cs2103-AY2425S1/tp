package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The API of the Model component, defining key methods for managing data, user preferences,
 * and backup operations within the application.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user preferences data with the data in {@code userPrefs}.
     *
     * @param userPrefs The new user preferences to set.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user preferences.
     *
     * @return The current user preferences.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the GUI settings from the user preferences.
     *
     * @return The GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the GUI settings in the user preferences.
     *
     * @param guiSettings The new GUI settings to apply.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the file path to the address book as set in user preferences.
     *
     * @return The path of the address book file.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the file path for the address book in user preferences.
     *
     * @param addressBookFilePath The path to store the address book file.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     *
     * @param addressBook The new address book data to set.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the current AddressBook.
     *
     * @return The current address book data.
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Checks if a person with the same identity as {@code person} exists in the address book.
     *
     * @param person The person to check.
     * @return true if the person exists, otherwise false.
     */
    boolean hasPerson(Person person);

    /**
     * Retrieves the calendar associated with the model.
     *
     * @return The calendar object.
     */
    Calendar getCalendar();

    /**
     * Checks if a person has an appointment in the calendar.
     *
     * @param person The person to check.
     * @return true if the person has an appointment, otherwise false.
     */
    boolean hasAppointment(Person person);

    /**
     * Deletes the specified person from the address book and updates the calendar.
     *
     * @param target The person to delete.
     */
    void deletePerson(Person target);

    /**
     * Adds a specified person to the address book and updates the calendar.
     *
     * @param person The person to add.
     */
    void addPerson(Person person);

    /**
     * Replaces the target person with the edited person in the address book.
     * Updates any associated appointments in the calendar.
     *
     * @param target       The person to be replaced.
     * @param editedPerson The new person to replace the target.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Retrieves the current operating hours.
     *
     * @return The operating hours object.
     */
    OperatingHours getOperatingHours();

    /**
     * Sets the operating hours and checks if the new hours are valid with existing appointments.
     *
     * @param openingHour The opening hour to set.
     * @param closingHour The closing hour to set.
     * @return true if the operating hours were successfully updated, otherwise false.
     */
    boolean setOperatingHours(LocalTime openingHour, LocalTime closingHour);

    /**
     * Checks if a specified appointment is within the operating hours.
     *
     * @param appointment The appointment to check.
     * @return true if the appointment is within operating hours, otherwise false.
     */
    boolean appointmentWithinOperatingHours(Appointment appointment);

    /**
     * Returns an unmodifiable view of the filtered person list.
     *
     * @return The filtered list of persons.
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list based on the specified predicate.
     *
     * @param predicate The predicate to use for filtering.
     * @throws NullPointerException If {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Creates a backup of the current patient record with a specified action description.
     *
     * @param actionDescription A description of the backup action.
     * @return The index used for the backup.
     * @throws CommandException If an error occurs during the backup process.
     */
    int backupData(String actionDescription) throws CommandException;

    /**
     * Restores the data from a backup specified by its index.
     *
     * @param index The index of the backup to restore.
     * @return The path of the restored backup file.
     * @throws IOException          If the backup file cannot be found or accessed.
     * @throws DataLoadingException If the backup data is invalid.
     */
    Path restoreBackup(int index) throws IOException, DataLoadingException;

    /**
     * Retrieves the Storage object associated with the model.
     *
     * @return The Storage object.
     */
    Storage getStorage();

    /**
     * Lists all available backup files in the backup directory.
     *
     * @return A formatted string listing all backup files.
     * @throws IOException If an error occurs while accessing the backup directory.
     */
    String listAllBackups() throws IOException;
}

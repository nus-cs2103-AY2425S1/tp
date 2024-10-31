package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;
import seedu.address.storage.BackupManager;
import seedu.address.storage.Storage;

/**
 * Represents the in-memory model of the address book data.
 * Handles core functionalities including data management, user preferences, backup, and restore operations.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final Storage storage;
    private final BackupManager backupManager;
    private final FilteredList<Person> filteredPersons;
    private final Calendar calendar;
    private OperatingHours operatingHours;

    /**
     * Initializes a ModelManager with the given address book, user preferences, and storage.
     * Creates a backup manager if storage is provided, otherwise defaults to the "backups" directory.
     *
     * @param addressBook The address book data to initialize the model with.
     * @param userPrefs   The user preferences to initialize the model with.
     * @param storage     The storage to be used by the model for backup and restore operations.
     * @throws IOException If an error occurs while initializing the backup manager.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyUserPrefs userPrefs,
                        Storage storage) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + ", user prefs: " + userPrefs
                + ", and storage: " + storage);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.storage = storage;
        if (storage != null) {
            this.backupManager = storage.getBackupManager();
        } else {
            this.backupManager = new BackupManager(Paths.get("backups"));
        }
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.calendar = new Calendar(this.addressBook);
        this.operatingHours = new OperatingHours(); // TBC currently only sets default
    }

    /**
     * Default constructor initializing ModelManager with empty AddressBook and UserPrefs.
     *
     * @throws IOException If an error occurs while initializing the backup manager.
     */
    public ModelManager() throws IOException {
        this(new AddressBook(), new UserPrefs(), null);
    }

    // ============ User Preferences Methods ============================================================

    /**
     * Sets user preferences with the provided ReadOnlyUserPrefs.
     *
     * @param userPrefs The new user preferences to set.
     */
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    /**
     * Retrieves the current user preferences.
     *
     * @return The user preferences stored in the model.
     */
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    /**
     * Gets the graphical user interface (GUI) settings.
     *
     * @return The GUI settings in user preferences.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    /**
     * Sets the GUI settings based on the provided GuiSettings object.
     *
     * @param guiSettings The new GUI settings to apply.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    /**
     * Gets the file path to the address book file.
     *
     * @return The path where the address book file is stored.
     */
    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    /**
     * Sets the file path for the address book data.
     *
     * @param addressBookFilePath The path to store the address book data.
     */
    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    // ============ Address Book Methods ================================================================

    /**
     * Sets the address book data to the provided ReadOnlyAddressBook.
     *
     * @param addressBook The new address book data to set.
     */
    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
        this.calendar.setAppointments(addressBook);
    }

    /**
     * Retrieves the current address book data.
     *
     * @return The current address book data.
     */
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /**
     * Checks if the specified person exists in the address book.
     *
     * @param person The person to check.
     * @return true if the person exists, otherwise false.
     */
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    /**
     * Retrieves the calendar associated with the model.
     *
     * @return The calendar object.
     */
    @Override
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Checks if a specified person has an appointment scheduled.
     *
     * @param person The person to check for an appointment.
     * @return true if the person has an appointment, otherwise false.
     */
    @Override
    public boolean hasAppointment(Person person) {
        return calendar.hasAppointment(person);
    }

    /**
     * Deletes a person from the address book and removes any associated appointment.
     * Triggers a backup before deletion.
     *
     * @param target The person to delete.
     */
    @Override
    public void deletePerson(Person target) {
        triggerBackup("delete_" + target.getName().fullName, target);
        addressBook.removePerson(target);
        calendar.deleteAppointment(target);
    }

    /**
     * Adds a person to the address book and creates an appointment for them.
     *
     * @param person The person to add.
     */
    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        calendar.addAppointment(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Replaces a target person with an edited person in the address book.
     * Updates the appointment for the edited person.
     *
     * @param target       The person to be replaced.
     * @param editedPerson The new person to replace the target.
     */
    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
        calendar.setAppointment(target, editedPerson);
    }

    /**
     * Retrieves the current operating hours.
     *
     * @return The operating hours object.
     */
    @Override
    public OperatingHours getOperatingHours() {
        return operatingHours;
    }

    /**
     * Sets the operating hours if the new hours do not conflict with any scheduled appointments.
     *
     * @param openingHour The opening hour.
     * @param closingHour The closing hour.
     * @return true if the operating hours were successfully updated, otherwise false.
     */
    @Override
    public boolean setOperatingHours(LocalTime openingHour, LocalTime closingHour) {
        OperatingHours newOperatingHours = new OperatingHours(openingHour, closingHour);
        if (newOperatingHours.isCalenderValid(calendar.getAppointments())) {
            operatingHours = newOperatingHours;
            return true;
        }
        return false;
    }

    /**
     * Checks if a given appointment falls within the operating hours.
     *
     * @param appointment The appointment to check.
     * @return true if the appointment is within operating hours, otherwise false.
     */
    @Override
    public boolean appointmentWithinOperatingHours(Appointment appointment) {
        requireNonNull(appointment);
        return operatingHours.isWithinOperatingHours(appointment);
    }

    // ============ Filtered Person List Accessors =======================================================

    /**
     * Retrieves the list of persons filtered by the current filter predicate.
     *
     * @return The filtered list of persons.
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Updates the predicate used for filtering the list of persons.
     *
     * @param predicate The predicate to use for filtering.
     */
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    // ============ Backup and Restore Methods ===========================================================

    /**
     * Creates a backup with an optional action description.
     *
     * @param actionDescription The description for the backup action.
     * @return The index of the created backup.
     * @throws CommandException If an error occurs during backup creation.
     */
    @Override
    public int backupData(String actionDescription) throws CommandException {
        if (storage == null) {
            throw new CommandException("Failed to create backup: Storage is not initialized!");
        }

        if (actionDescription == null || actionDescription.isBlank()) {
            // Use default description if none provided
            actionDescription = "manual_backup";
        }

        try {
            int backupIndex = backupManager.createIndexedBackup(storage.getAddressBookFilePath(), actionDescription);
            logger.info("Manual backup created at index " + backupIndex + ": " + actionDescription);
            return backupIndex;
        } catch (IOException e) {
            logger.warning("Manual backup failed: " + e.getMessage());
            throw new CommandException("Failed to create manual backup: " + e.getMessage());
        }
    }

    /**
     * Triggers a backup for a specified action, including the target person’s details.
     *
     * @param actionDescription A description for the action being backed up.
     * @param target            The person involved in the action.
     */
    protected void triggerBackup(String actionDescription, Person target) {
        try {
            int index = backupManager.createIndexedBackup(storage.getAddressBookFilePath(), actionDescription);
            logger.info("Backup triggered for action: " + actionDescription + " at index " + index);
        } catch (IOException e) {
            logger.warning("Backup failed for action: " + actionDescription + " - " + e.getMessage());
        }
    }

    /**
     * Restores data from a backup file specified by its index.
     *
     * @param index The index of the backup to restore.
     * @return The path of the restored backup file.
     * @throws IOException          If the backup file cannot be found.
     * @throws DataLoadingException If the backup data is invalid.
     */
    @Override
    public Path restoreBackup(int index) throws IOException, DataLoadingException {
        if (storage == null) {
            throw new IOException("Storage is not initialized!");
        }

        Path backupPath = backupManager.restoreBackupByIndex(index);
        if (backupPath == null || !Files.exists(backupPath)) {
            throw new IOException("Backup file not found at index " + index);
        }

        // Read the backup data
        ReadOnlyAddressBook backupData = storage.readAddressBook(backupPath)
                .orElseThrow(() -> new DataLoadingException(new Exception("Backup file is invalid")));

        // Set the model's address book to the backup data
        setAddressBook(backupData);

        return backupPath;
    }

    /**
     * Lists all available backup files.
     *
     * @return A string listing all backup files.
     * @throws IOException If an error occurs during file retrieval.
     */
    @Override
    public String listAllBackups() throws IOException {
        if (storage == null) {
            throw new IOException("Storage is not initialized!");
        }
        return storage.listBackups();
    }

    // ============ Equality and Storage Access Methods ==================================================

    /**
     * Gets the storage associated with this model manager.
     *
     * @return The storage object.
     */
    @Override
    public Storage getStorage() {
        return storage;
    }

    /**
     * Compares this ModelManager to another object for equality.
     *
     * @param other The object to compare.
     * @return true if the other object is a ModelManager with equal fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && calendar.equals(otherModelManager.calendar);
    }
}

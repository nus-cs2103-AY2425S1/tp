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
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final Storage storage;
    private final BackupManager backupManager;
    private final FilteredList<Person> filteredPersons;
    private final Calendar calendar;

    /**
     * Initializes a ModelManager with the given address book, user preferences, and storage.
     *
     * @param addressBook The address book data to initialize the model with.
     * @param userPrefs   The user preferences to initialize the model with.
     * @param storage     The storage to be used by the model for backup and restore operations.
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
    }

    public ModelManager() throws IOException {
        this(new AddressBook(), new UserPrefs(), null);
    }

    // ============ User Preferences Methods ============================================================

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

    // ============ Address Book Methods ================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
        this.calendar.setAppointments(addressBook);
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
    public Calendar getCalendar() {
        return calendar;
    }



    @Override
    public boolean hasAppointment(Person person) {
        return calendar.hasAppointment(person);
    }

    @Override
    public void deletePerson(Person target) {
        triggerBackup("delete_" + target.getName().fullName, target);
        addressBook.removePerson(target);
        calendar.deleteAppointment(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        calendar.addAppointment(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
        calendar.setAppointment(target, editedPerson);
    }


    private void triggerBackup(String actionDescription, Person target) {
        try {
            int index = backupManager.createIndexedBackup(storage.getAddressBookFilePath(), actionDescription);
            logger.info("Backup triggered for action: " + actionDescription + " at index " + index);
        } catch (IOException e) {
            logger.warning("Backup failed for action: " + actionDescription + " - " + e.getMessage());
        }
    }

    @Override
    public OperatingHours getOperatingHours() {
        return addressBook.getOperatingHours();
    }

    @Override
    public boolean setOperatingHours(LocalTime openingHour, LocalTime closingHour) {
        OperatingHours newOperatingHours = new OperatingHours(openingHour, closingHour);
        if (newOperatingHours.isCalenderValid(calendar.getAppointments())) {
            addressBook.setOperatingHours(newOperatingHours);
            return true;
        }
        return false;
    }

    @Override
    public boolean appointmentWithinOperatingHours(Appointment appointment) {
        requireNonNull(appointment);
        return addressBook.getOperatingHours().isWithinOperatingHours(appointment);
    }

    // ============ Filtered Person List Accessors =======================================================

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
    public String listAllBackups() throws IOException {
        if (storage == null) {
            throw new IOException("Storage is not initialized!");
        }
        return storage.listBackups();
    }

    // ============ Backup and Restore Methods ===========================================================

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

    // ============ Equality and Storage Access Methods ==================================================

    @Override
    public Storage getStorage() {
        return storage;
    }

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

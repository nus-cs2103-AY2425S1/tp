package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
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
        this.backupManager = new BackupManager(Paths.get("backups"));
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
        triggerBackup("delete", target);
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

    /*@Override
    public void backupData(String filePath) throws IOException {
        synchronized (backupManager) {
            logger.info("Starting manual backup.");

            if (storage == null) {
                throw new IOException("Storage is not initialized!");
            }

            // Clean up previous manual backup to ensure only one manual backup exists
            backupManager.cleanOldBackups(1); // This only affects manual backups

            // Use a fixed filename for manual backups
            if (filePath == null) {
                filePath = "backups/clinicbuddy-manual-backup.json"; // Fixed name for manual backup
            }

            logger.info("Manual backup triggered to path: " + filePath);

            // Save the AddressBook to the manual backup file
            storage.saveAddressBook(getAddressBook(), Paths.get(filePath));

            logger.info("Manual backup completed.");
        }
    }*/

    // Automatically trigger backup after operations
    protected void triggerBackup(String action, Person target) {
        try {
            String backupName = action + " " + target.getName();
            backupManager.triggerBackup(storage.getAddressBookFilePath(), backupName);
            logger.info("Backup triggered: " + backupName);
        } catch (IOException e) {
            logger.warning("Backup failed: " + e.getMessage());
        }
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

    // ============ Backup and Restore Methods ===========================================================

    /**
     * Cleans up old backups, retaining only the latest `maxBackups`.
     *
     * @param maxBackups The number of backups to retain.
     * @throws IOException If there is an error during cleanup.
     */
    public void cleanOldBackups(int maxBackups) throws IOException {
        if (storage != null) {
            logger.info("Cleaning old backups, keeping the latest " + maxBackups + " backups.");
            storage.cleanOldBackups(maxBackups);
        } else {
            throw new IOException("Storage is not initialized!");
        }
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

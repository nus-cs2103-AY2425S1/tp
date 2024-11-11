package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data and backup functionality.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    protected BackupManager backupManager;
    private final AddressBookStorage addressBookStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given storage and initializes backup management.
     *
     * @param addressBookStorage Storage for AddressBook.
     * @param userPrefsStorage   Storage for User preferences.
     * @throws IOException If the backup directory cannot be initialized.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) throws IOException {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        // Initialize BackupManager with the backup directory "backups"
        this.backupManager = new BackupManager(Path.of("backups"));
    }

    /**
     * Sets the BackupManager used by the StorageManager.
     *
     * @param backupManager The BackupManager instance to set.
     */
    public void setBackupManager(BackupManager backupManager) {
        this.backupManager = backupManager;
    }

    /**
     * Retrieves the BackupManager used by this StorageManager.
     *
     * @return The BackupManager instance responsible for managing backup operations.
     */
    @Override
    public BackupManager getBackupManager() {
        return this.backupManager;
    }

    // =================== User Preferences Methods ===================

    /**
     * Gets the file path for user preferences storage.
     *
     * @return The file path of user preferences.
     */
    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    /**
     * Reads the user preferences from storage.
     *
     * @return An Optional containing UserPrefs if present, or empty if unavailable.
     * @throws DataLoadingException If there is an error during data loading.
     */
    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    /**
     * Saves the provided user preferences to storage.
     *
     * @param userPrefs The user preferences to save.
     * @throws IOException If there is an error during saving.
     */
    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // =================== AddressBook Methods ===================

    /**
     * Gets the file path for AddressBook storage.
     *
     * @return The file path of the AddressBook data.
     */
    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    /**
     * Reads the AddressBook data from storage.
     *
     * @return An Optional containing ReadOnlyAddressBook if present, or empty if unavailable.
     * @throws DataLoadingException If there is an error during data loading.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    /**
     * Reads the AddressBook data from the specified file path.
     *
     * @param filePath The file path to read the AddressBook data from.
     * @return An Optional containing ReadOnlyAddressBook if present, or empty if unavailable.
     * @throws DataLoadingException If there is an error during data loading.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read AddressBook from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    /**
     * Saves the provided AddressBook data to the default storage path.
     *
     * @param addressBook The AddressBook data to save.
     * @throws IOException If there is an error during saving.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    /**
     * Saves the provided AddressBook data to the specified file path.
     *
     * @param addressBook The AddressBook data to save.
     * @param filePath    The file path where the data should be saved.
     * @throws IOException If there is an error during saving.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // =================== Backup and Restore Methods ===================

    /**
     * Restores a backup by its index.
     *
     * @param index The index of the backup to restore.
     * @return The path to the restored backup file.
     * @throws IOException If the backup file is not found or cannot be accessed.
     */
    public Path restoreBackup(int index) throws IOException {
        return backupManager.restoreBackupByIndex(index);
    }

    /**
     * Cleans up old backups, retaining only the specified number of the most recent backups.
     *
     * @param maxBackups The number of recent backups to retain.
     * @throws IOException If there is an error during cleanup.
     */
    @Override
    public void cleanOldBackups(int maxBackups) throws IOException {
        backupManager.cleanOldBackups(maxBackups);
    }

    /**
     * Creates a backup of the address book data with a given description.
     *
     * @param actionDescription Description for the backup.
     * @return The index used for the backup.
     * @throws IOException If an error occurs during backup creation.
     */
    public int createBackup(String actionDescription) throws IOException {
        Path sourcePath = getAddressBookFilePath();
        return backupManager.createIndexedBackup(sourcePath, actionDescription);
    }

    /**
     * Lists all available backups in a formatted manner.
     *
     * @return A string listing all backup files in the /backups/ directory.
     * @throws IOException If an error occurs while accessing the backup directory.
     */
    @Override
    public String listBackups() throws IOException {
        return backupManager.getFormattedBackupList();
    }

}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
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

    public void setBackupManager(BackupManager backupManager) {
        this.backupManager = backupManager;
    }

    // =================== User Preferences Methods ===================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // =================== AddressBook Methods ===================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read AddressBook from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // =================== Backup and Restore Methods ===================

    @Override
    public Optional<Path> restoreBackup() throws IOException {
        // Attempt to restore the most recent backup
        List<Path> backups = backupManager.listBackups();
        if (backups.isEmpty()) {
            logger.warning("No backup available to restore.");
            return Optional.empty();
        }

        // Find the backup with the highest index (most recent based on index rotation)
        int latestIndex = backups.stream()
                .mapToInt(backupManager::extractIndex)
                .max()
                .orElse(-1);

        if (latestIndex == -1) {
            logger.warning("No valid backup indices found.");
            return Optional.empty();
        }

        try {
            Path backupPath = backupManager.restoreBackupByIndex(latestIndex);
            return Optional.of(backupPath);
        } catch (IOException e) {
            logger.warning("Failed to restore backup: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Restores a backup by its index.
     *
     * @param index The index of the backup to restore.
     * @return The path to the backup file.
     * @throws IOException If the backup file is not found or cannot be accessed.
     */
    public Path restoreBackup(int index) throws IOException {
        return backupManager.restoreBackupByIndex(index);
    }

    @Override
    public void cleanOldBackups(int maxBackups) throws IOException {
        backupManager.cleanOldBackups(maxBackups);
    }

    /**
     * Creates a backup of the address book data.
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
     * Lists all available backups with their indices.
     *
     * @return A list of backup file paths.
     * @throws IOException If an error occurs while accessing the backup directory.
     */
    public List<Path> listBackups() throws IOException {
        return backupManager.listBackups();
    }
}

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
    private final AddressBookStorage addressBookStorage;
    private final UserPrefsStorage userPrefsStorage;
    private final BackupManager backupManager;

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
        this.backupManager = new BackupManager(Path.of("backups")); // Use "backups" directory.
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
        logger.fine("Reading AddressBook from: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        Path filePath = addressBookStorage.getAddressBookFilePath();
        saveAddressBook(addressBook, filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Saving AddressBook to: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // =================== Backup and Restore Methods ===================

    /**
     * Restores the most recent backup of the AddressBook, if available.
     *
     * @return Optional containing the restored backup path, if any.
     * @throws IOException If restoration fails.
     */
    public Optional<Path> restoreBackup() throws IOException {
        Optional<Path> restoredBackup = backupManager.restoreMostRecentBackup();
        if (restoredBackup.isPresent()) {
            logger.info("Restored from backup: " + restoredBackup.get());
        } else {
            logger.warning("No backup available to restore.");
        }
        return restoredBackup;
    }

    /**
     * Cleans up old backups, keeping only the most recent `maxBackups`.
     *
     * @param maxBackups The number of backups to retain.
     * @throws IOException If cleanup fails.
     */
    public void cleanOldBackups(int maxBackups) throws IOException {
        backupManager.cleanOldBackups(maxBackups);
    }

}

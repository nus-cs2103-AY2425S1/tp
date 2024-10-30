package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component, handling storage operations for user preferences, address book data,
 * and backup management.
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    /**
     * Reads user preferences from the storage.
     *
     * @return An Optional containing UserPrefs if present, or empty if unavailable.
     * @throws DataLoadingException If there is an error during data loading.
     */
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    /**
     * Saves the provided user preferences to the storage.
     *
     * @param userPrefs The user preferences to save.
     * @throws IOException If there is an error during saving.
     */
    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    /**
     * Gets the file path where the address book data is stored.
     *
     * @return The path to the address book file.
     */
    @Override
    Path getAddressBookFilePath();

    /**
     * Reads the address book data from the storage.
     *
     * @return An Optional containing the ReadOnlyAddressBook if present, or empty if unavailable.
     * @throws DataLoadingException If there is an error during data loading.
     */
    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    /**
     * Saves the provided address book data to the default storage path.
     *
     * @param addressBook The address book data to save.
     * @throws IOException If there is an error during saving.
     */
    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the provided address book data to the specified file path.
     *
     * @param addressBook The address book data to save.
     * @param filePath    The file path where the address book data should be saved.
     * @throws IOException If there is an error during saving.
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

    /**
     * Cleans up old backups, retaining only the specified number of the most recent backups.
     *
     * @param maxBackups The number of recent backups to retain.
     * @throws IOException If there is an error during cleanup.
     */
    void cleanOldBackups(int maxBackups) throws IOException;

    /**
     * Lists all available backups in a formatted manner.
     *
     * @return A string listing all backup files in the /backups/ directory.
     * @throws IOException If an error occurs while accessing the backup directory.
     */
    String listBackups() throws IOException;

    /**
     * Returns the BackupManager used by this Storage.
     *
     * @return The BackupManager instance.
     */
    BackupManager getBackupManager();

}

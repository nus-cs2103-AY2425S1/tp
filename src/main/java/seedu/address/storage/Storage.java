package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

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

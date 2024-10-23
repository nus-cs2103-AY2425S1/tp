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
     * Restores the most recent backup of the address book, if available.
     *
     * @return An Optional containing the Path to the most recent backup, if found.
     * @throws IOException If there is an error accessing the backup.
     */
    Optional<Path> restoreBackup() throws IOException;

    /**
     * Cleans up old backups, retaining only the specified number of the most recent backups.
     *
     * @param maxBackups The number of recent backups to retain.
     * @throws IOException If there is an error during cleanup.
     */
    void cleanOldBackups(int maxBackups) throws IOException;

}

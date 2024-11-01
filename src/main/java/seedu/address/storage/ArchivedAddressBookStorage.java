package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for ArchivedAddressBook.
 */
public interface ArchivedAddressBookStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getArchivedAddressBookFilePath();

    /**
     * Returns ArchivedAddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyAddressBook> readArchivedAddressBook() throws DataLoadingException;

    /**
     * @see #getArchivedAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readArchivedAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveArchivedAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveArchivedAddressBook(ReadOnlyAddressBook)
     */
    void saveArchivedAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;
}

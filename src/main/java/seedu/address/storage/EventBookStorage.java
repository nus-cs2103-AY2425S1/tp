package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface EventBookStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getEventBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyEventBook> readEventBook() throws DataLoadingException;

    /**
     * @see #getEventBookFilePath()
     */
    Optional<ReadOnlyEventBook> readEventBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEventBook} to the storage.
     * @param eventBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventBook(ReadOnlyEventBook eventBook) throws IOException;

    /**
     * @see #saveEventBook(ReadOnlyEventBook)
     */
    void saveEventBook(ReadOnlyEventBook eventBook, Path filePath) throws IOException;

}

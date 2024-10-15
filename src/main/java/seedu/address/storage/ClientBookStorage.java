package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyClientBook;

/**
 * Represents a storage for {@link seedu.address.model.ClientBook}.
 */
public interface ClientBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getClientBookFilePath();

    /**
     * Returns ClientBook data as a {@link ReadOnlyClientBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyClientBook> readClientBook() throws DataLoadingException;

    /**
     * @see #getClientBookFilePath()
     */
    Optional<ReadOnlyClientBook> readClientBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyClientBook} to the storage.
     * @param clientBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveClientBook(ReadOnlyClientBook clientBook) throws IOException;

    /**
     * @see #saveClientBook(ReadOnlyClientBook)
     */
    void saveClientBook(ReadOnlyClientBook clientBook, Path filePath) throws IOException;

}

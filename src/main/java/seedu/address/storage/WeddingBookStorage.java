package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyWeddingBook;

/**
 * Represents a storage for {@link seedu.address.model.WeddingBook}.
 */
public interface WeddingBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWeddingBookFilePath();

    /**
     * Returns WeddingBook data as a {@link ReadOnlyWeddingBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyWeddingBook> readWeddingBook() throws DataLoadingException;

    /**
     * @see #getWeddingBookFilePath()
     */
    Optional<ReadOnlyWeddingBook> readWeddingBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyWeddingBook} to the storage.
     * @param weddingBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWeddingBook(ReadOnlyWeddingBook weddingBook) throws IOException;

    /**
     * @see #saveWeddingBook(ReadOnlyWeddingBook)
     */
    void saveWeddingBook(ReadOnlyWeddingBook weddingBook, Path filePath) throws IOException;
}

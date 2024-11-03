package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPrudy;

/**
 * Represents a storage for {@link seedu.address.model.Prudy}.
 */
public interface PrudyStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPrudyFilePath();

    /**
     * Returns Prudy data as a {@link ReadOnlyPrudy}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPrudy> readPrudy() throws DataLoadingException;

    /**
     * @see #getPrudyFilePath()
     */
    Optional<ReadOnlyPrudy> readPrudy(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPrudy} to the storage.
     * @param prudy cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePrudy(ReadOnlyPrudy prudy) throws IOException;

    /**
     * @see #savePrudy(ReadOnlyPrudy)
     */
    void savePrudy(ReadOnlyPrudy prudy, Path filePath) throws IOException;

}

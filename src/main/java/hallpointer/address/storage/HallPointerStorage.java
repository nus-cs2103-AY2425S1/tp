package hallpointer.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import hallpointer.address.commons.exceptions.DataLoadingException;
import hallpointer.address.model.HallPointer;
import hallpointer.address.model.ReadOnlyHallPointer;

/**
 * Represents a storage for {@link HallPointer}.
 */
public interface HallPointerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHallPointerFilePath();

    /**
     * Returns HallPointer data as a {@link ReadOnlyHallPointer}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyHallPointer> readHallPointer() throws DataLoadingException;

    /**
     * @see #getHallPointerFilePath()
     */
    Optional<ReadOnlyHallPointer> readHallPointer(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyHallPointer} to the storage.
     * @param hallPointer cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHallPointer(ReadOnlyHallPointer hallPointer) throws IOException;

    /**
     * @see #saveHallPointer(ReadOnlyHallPointer)
     */
    void saveHallPointer(ReadOnlyHallPointer hallPointer, Path filePath) throws IOException;

}

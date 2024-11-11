package seedu.eventtory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eventtory.commons.exceptions.DataLoadingException;
import seedu.eventtory.model.ReadOnlyEventTory;

/**
 * Represents a storage for {@link seedu.eventtory.model.EventTory}.
 */
public interface EventToryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEventToryFilePath();

    /**
     * Returns EventTory data as a {@link seedu.eventtory.model.ReadOnlyEventTory}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyEventTory> readEventTory() throws DataLoadingException;

    /**
     * @see #getEventToryFilePath()
     */
    Optional<ReadOnlyEventTory> readEventTory(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEventTory} to the storage.
     * @param eventTory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventTory(ReadOnlyEventTory eventTory) throws IOException;

    /**
     * @see #saveEventTory(ReadOnlyEventTory)
     */
    void saveEventTory(ReadOnlyEventTory eventTory, Path filePath) throws IOException;

}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.event.ReadOnlyEventManager;

/**
 * Interface for storage of {@link seedu.address.model.event.EventManager}
 */
public interface EventManagerStorage {
    /**
     * Returns the file path of data file.
     */
    Path getEventManagerFilePath();

    /**
     * Returns EventManger data as a {@link seedu.address.model.event.ReadOnlyEventManager}
     * Returns {@code Optional.empty()} if storage is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed
     */
    Optional<ReadOnlyEventManager> readEventManager() throws DataLoadingException;

    /**
     * @see #getEventManagerFilePath()
     */
    Optional<ReadOnlyEventManager> readEventManager(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEventManager} to the storage.
     * @param eventManager cannot be Null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventManager(ReadOnlyEventManager eventManager) throws IOException;

    /**
     * @see #saveEventManager(ReadOnlyEventManager)
     */
    void saveEventManager(ReadOnlyEventManager eventManager, Path filePath) throws IOException;

}

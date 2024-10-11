package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ClientHub;
import seedu.address.model.ReadOnlyClientHub;

/**
 * Represents a storage for {@link ClientHub}.
 */
public interface ClientHubStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getClientHubFilePath();

    /**
     * Returns ClientHub data as a {@link ReadOnlyClientHub}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyClientHub> readClientHub() throws DataLoadingException;

    /**
     * @see #getClientHubFilePath()
     */
    Optional<ReadOnlyClientHub> readClientHub(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyClientHub} to the storage.
     * @param clientHub cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveClientHub(ReadOnlyClientHub clientHub) throws IOException;

    /**
     * @see #saveClientHub(ReadOnlyClientHub) (ReadOnlyClientHub)
     */
    void saveClientHub(ReadOnlyClientHub clientHub, Path filePath) throws IOException;

}

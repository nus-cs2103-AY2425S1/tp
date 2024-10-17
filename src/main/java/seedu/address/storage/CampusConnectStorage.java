package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.CampusConnect;
import seedu.address.model.ReadOnlyCampusConnect;

/**
 * Represents a storage for {@link CampusConnect}.
 */
public interface CampusConnectStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCampusConnectFilePath();

    /**
     * Returns CampusConnect data as a {@link ReadOnlyCampusConnect}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyCampusConnect> readCampusConnect() throws DataLoadingException;

    /**
     * @see #getCampusConnectFilePath()
     */
    Optional<ReadOnlyCampusConnect> readCampusConnect(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyCampusConnect} to the storage.
     * @param campusConnect cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCampusConnect(ReadOnlyCampusConnect campusConnect) throws IOException;

    /**
     * @see #saveCampusConnect(ReadOnlyCampusConnect)
     */
    void saveCampusConnect(ReadOnlyCampusConnect campusConnect, Path filePath) throws IOException;

}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyClinicConnectSystem;

/**
 * Represents a storage for {@link seedu.address.model.ClinicConnectSystem}.
 */
public interface ClinicConnectSystemStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getClinicConnectSystemFilePath();

    /**
     * Returns ClinicConnectSystem data as a {@link ReadOnlyClinicConnectSystem}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyClinicConnectSystem> readClinicConnectSystem() throws DataLoadingException;

    /**
     * @see #getClinicConnectSystemFilePath()
     */
    Optional<ReadOnlyClinicConnectSystem> readClinicConnectSystem(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyClinicConnectSystem} to the storage.
     * @param clinicConnectSystem cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem) throws IOException;

    /**
     * @see #saveClinicConnectSystem(ReadOnlyClinicConnectSystem)
     */
    void saveClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem, Path filePath) throws IOException;

}

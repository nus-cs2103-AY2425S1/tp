package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.PawPatrol;
import seedu.address.model.ReadOnlyPawPatrol;

/**
 * Represents a storage for {@link PawPatrol}.
 */
public interface PawPatrolStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPawPatrolFilePath();

    /**
     * Returns PawPatrol data as a {@link ReadOnlyPawPatrol}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPawPatrol> readPawPatrol() throws DataLoadingException;

    /**
     * @see #getPawPatrolFilePath()
     */
    Optional<ReadOnlyPawPatrol> readPawPatrol(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPawPatrol} to the storage.
     * @param pawPatrol cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePawPatrol(ReadOnlyPawPatrol pawPatrol) throws IOException;

    /**
     * @see #savePawPatrol(ReadOnlyPawPatrol)
     */
    void savePawPatrol(ReadOnlyPawPatrol pawPatrol, Path filePath) throws IOException;

}

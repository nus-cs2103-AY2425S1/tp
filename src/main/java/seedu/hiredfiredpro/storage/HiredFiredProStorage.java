package seedu.hiredfiredpro.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.hiredfiredpro.commons.exceptions.DataLoadingException;
import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;

/**
 * Represents a storage for {@link HiredFiredPro}.
 */
public interface HiredFiredProStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHiredFiredProFilePath();

    /**
     * Returns HiredFiredPro data as a {@link ReadOnlyHiredFiredPro}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyHiredFiredPro> readHiredFiredPro() throws DataLoadingException;

    /**
     * @see #getHiredFiredProFilePath()
     */
    Optional<ReadOnlyHiredFiredPro> readHiredFiredPro(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyHiredFiredPro} to the storage.
     * @param hiredFiredPro cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro) throws IOException;

    /**
     * @see #saveHiredFiredPro(ReadOnlyHiredFiredPro)
     */
    void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro, Path filePath) throws IOException;

}

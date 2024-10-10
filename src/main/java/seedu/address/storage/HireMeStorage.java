package seedu.address.storage;


import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyHireMe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for HireMe
 */
public interface HireMeStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getHireMeFilePath();


    /**
     * Returns HireMe data as a {@link ReadOnlyHireMe}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyHireMe> readHireMe() throws DataLoadingException;

    /**
     * @see #getHireMeFilePath()
     */
    Optional<ReadOnlyHireMe> readHireMe(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyHireMe} to the storage.
     * @param hireMe cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHireMe(ReadOnlyHireMe hireMe) throws IOException;

    /**
     * @see #saveHireMe(ReadOnlyHireMe)
     */
    void saveHireMe(ReadOnlyHireMe hireMe, Path filePath) throws IOException;

}

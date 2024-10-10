package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyHireMe;

/**
 * Represents a storage for HireMe.
 */
public interface HireMeStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getHireMeFilePath();

    Optional<ReadOnlyHireMe> readHireMe() throws DataLoadingException;

    Optional<ReadOnlyHireMe> readHireMe(Path filePath) throws DataLoadingException;

    void saveHireMe(ReadOnlyHireMe hireMe) throws IOException;

    void saveHireMe(ReadOnlyHireMe hireMe, Path filePath) throws IOException;
}

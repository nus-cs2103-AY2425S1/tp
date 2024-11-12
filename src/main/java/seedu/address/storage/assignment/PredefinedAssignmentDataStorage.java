package seedu.address.storage.assignment;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.assignment.PredefinedAssignment;
import seedu.address.model.assignment.ReadOnlyPredefinedAssignmentsData;

/**
 * Represents a storage for {@link seedu.address.model.assignment.PredefinedAssignmentsData}.
 */
public interface PredefinedAssignmentDataStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAssignmentFilePath();

    /**
     * Returns Assignment data as a {@link PredefinedAssignment}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPredefinedAssignmentsData> readAssignment() throws DataLoadingException;

    /**
     * @see #getAssignmentFilePath() ()
     */
    Optional<ReadOnlyPredefinedAssignmentsData> readAssignment(Path filePath) throws DataLoadingException;
}

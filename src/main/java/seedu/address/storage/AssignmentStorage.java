package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.assignment.AssignmentList;

/**
 * Represents a storage for assignments.
 * This interface defines methods for reading and writing assignments to and from a storage medium.
 */
public interface AssignmentStorage {

    /**
     * Returns the file path of the assignment storage file.
     *
     * @return the file path of the assignment storage.
     */
    Path getAssignmentFilePath();

    /**
     * Reads assignments from the storage file.
     *
     * @return an {@code Optional<AssignmentList>} containing the assignments if successful,
     *         or an empty Optional if no assignments are found.
     * @throws DataLoadingException if there is an issue loading the data from the storage.
     */
    Optional<AssignmentList> readAssignments() throws DataLoadingException;

    /**
     * Reads assignments from the specified storage file.
     *
     * @param filePath the path of the file to read assignments from.
     * @return an {@code Optional<AssignmentList>} containing the assignments if successful,
     *         or an empty Optional if no assignments are found.
     * @throws DataLoadingException if there is an issue loading the data from the specified file.
     */
    Optional<AssignmentList> readAssignments(Path filePath) throws DataLoadingException;

    /**
     * Saves the given assignments to the default storage file.
     *
     * @param assignmentList the {@code AssignmentList} to save.
     * @throws IOException if there is an issue writing to the storage file.
     */

    void saveAssignments(AssignmentList assignmentList) throws IOException;

    /**
     * Saves the given assignments to the specified storage file.
     *
     * @param assignmentList the {@code AssignmentList} to save.
     * @param filePath the path of the file to save assignments to.
     * @throws IOException if there is an issue writing to the specified file.
     */
    void saveAssignments(AssignmentList assignmentList, Path filePath) throws IOException;
}

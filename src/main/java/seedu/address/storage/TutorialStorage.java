package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.tut.Tut;

/**
 * Represents a storage for tutorials.
 * This interface defines methods for reading and writing tutorials to and from a storage medium.
 */
public interface TutorialStorage {

    /**
     * Returns the file path of the tutorial storage file.
     *
     * @return the file path of the tutorial storage.
     */
    Path getTutorialFilePath();

    /**
     * Reads tutorials from the storage file.
     *
     * @return an {@code Optional<List<Tut>>} containing the tutorials if successful,
     *         or an empty Optional if no tutorials are found.
     * @throws DataLoadingException if there is an issue loading the data from the storage.
     */
    Optional<List<Tut>> readTutorials() throws DataLoadingException;

    /**
     * Reads tutorials from the specified storage file.
     *
     * @param filePath the path of the file to read tutorials from.
     * @return an {@code Optional<List<Tut>>} containing the tutorials if successful,
     *         or an empty Optional if no tutorials are found.
     * @throws DataLoadingException if there is an issue loading the data from the specified file.
     */
    Optional<List<Tut>> readTutorials(Path filePath) throws DataLoadingException;

    /**
     * Saves the given tutorials to the default storage file.
     *
     * @param tutorialList the {@code List<Tut>} to save.
     * @throws IOException if there is an issue writing to the storage file.
     */
    void saveTutorials(List<Tut> tutorialList) throws IOException;

    /**
     * Saves the given tutorials to the specified storage file.
     *
     * @param tutorialList the {@code List<Tut>} to save.
     * @param filePath the path of the file to save tutorials to.
     * @throws IOException if there is an issue writing to the specified file.
     */
    void saveTutorials(List<Tut> tutorialList, Path filePath) throws IOException;
}

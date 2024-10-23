package tutorease.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.model.ReadOnlyTutorEase;

/**
 * Represents a storage for {@link tutorease.address.model.TutorEase}.
 */
public interface TutorEaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTutorEaseFilePath();

    /**
     * Returns TutorEase data as a {@link ReadOnlyTutorEase}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTutorEase> readTutorEase() throws DataLoadingException;

    /**
     * @see #getTutorEaseFilePath()
     */
    Optional<ReadOnlyTutorEase> readTutorEase(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTutorEase} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTutorEase(ReadOnlyTutorEase addressBook) throws IOException;

    /**
     * @see #saveTutorEase(ReadOnlyTutorEase)
     */
    void saveTutorEase(ReadOnlyTutorEase addressBook, Path filePath) throws IOException;

}

package seedu.academyassist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.academyassist.commons.exceptions.DataLoadingException;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.ReadOnlyAcademyAssist;

/**
 * Represents a storage for {@link AcademyAssist}.
 */
public interface AcademyAssistStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAcademyAssistFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAcademyAssist}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyAcademyAssist> readAcademyAssist() throws DataLoadingException;

    /**
     * @see #getAcademyAssistFilePath()
     */
    Optional<ReadOnlyAcademyAssist> readAcademyAssist(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyAcademyAssist} to the storage.
     * @param academyAssist cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAcademyAssist(ReadOnlyAcademyAssist academyAssist) throws IOException;

    /**
     * @see #saveAcademyAssist(ReadOnlyAcademyAssist)
     */
    void saveAcademyAssist(ReadOnlyAcademyAssist addressBook, Path filePath) throws IOException;

}

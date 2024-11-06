package seedu.academyassist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.academyassist.commons.exceptions.DataLoadingException;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.ReadOnlyUserPrefs;
import seedu.academyassist.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AcademyAssistStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAcademyAssistFilePath();

    @Override
    Optional<ReadOnlyAcademyAssist> readAcademyAssist() throws DataLoadingException;

    @Override
    void saveAcademyAssist(ReadOnlyAcademyAssist academyAssist) throws IOException;

}

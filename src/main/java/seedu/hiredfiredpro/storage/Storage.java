package seedu.hiredfiredpro.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.hiredfiredpro.commons.exceptions.DataLoadingException;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;
import seedu.hiredfiredpro.model.ReadOnlyUserPrefs;
import seedu.hiredfiredpro.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends HiredFiredProStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getHiredFiredProFilePath();

    @Override
    Optional<ReadOnlyHiredFiredPro> readHiredFiredPro() throws DataLoadingException;

    @Override
    void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro) throws IOException;

}

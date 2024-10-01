package keycontacts.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import keycontacts.commons.exceptions.DataLoadingException;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.ReadOnlyUserPrefs;
import keycontacts.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends StudentDirectoryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getStudentDirectoryFilePath();

    @Override
    Optional<ReadOnlyStudentDirectory> readStudentDirectory() throws DataLoadingException;

    @Override
    void saveStudentDirectory(ReadOnlyStudentDirectory studentDirectory) throws IOException;

}

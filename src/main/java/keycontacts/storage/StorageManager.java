package keycontacts.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import keycontacts.commons.core.LogsCenter;
import keycontacts.commons.exceptions.DataLoadingException;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.ReadOnlyUserPrefs;
import keycontacts.model.UserPrefs;

/**
 * Manages storage of StudentDirectory data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private StudentDirectoryStorage studentDirectoryStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code StudentDirectoryStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(StudentDirectoryStorage studentDirectoryStorage, UserPrefsStorage userPrefsStorage) {
        this.studentDirectoryStorage = studentDirectoryStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ StudentDirectory methods ==============================

    @Override
    public Path getStudentDirectoryFilePath() {
        return studentDirectoryStorage.getStudentDirectoryFilePath();
    }

    @Override
    public Optional<ReadOnlyStudentDirectory> readStudentDirectory() throws DataLoadingException {
        return readStudentDirectory(studentDirectoryStorage.getStudentDirectoryFilePath());
    }

    @Override
    public Optional<ReadOnlyStudentDirectory> readStudentDirectory(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return studentDirectoryStorage.readStudentDirectory(filePath);
    }

    @Override
    public void saveStudentDirectory(ReadOnlyStudentDirectory studentDirectory) throws IOException {
        saveStudentDirectory(studentDirectory, studentDirectoryStorage.getStudentDirectoryFilePath());
    }

    @Override
    public void saveStudentDirectory(ReadOnlyStudentDirectory studentDirectory, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        studentDirectoryStorage.saveStudentDirectory(studentDirectory, filePath);
    }

}

package seedu.academyassist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.academyassist.commons.core.LogsCenter;
import seedu.academyassist.commons.exceptions.DataLoadingException;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.ReadOnlyUserPrefs;
import seedu.academyassist.model.UserPrefs;

/**
 * Manages storage of AcademyAssist data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AcademyAssistStorage academyAssistStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AcademyAssistStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AcademyAssistStorage academyAssistStorage, UserPrefsStorage userPrefsStorage) {
        this.academyAssistStorage = academyAssistStorage;
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


    // ================ AcademyAssist methods ==============================

    @Override
    public Path getAcademyAssistFilePath() {
        return academyAssistStorage.getAcademyAssistFilePath();
    }

    @Override
    public Optional<ReadOnlyAcademyAssist> readAcademyAssist() throws DataLoadingException {
        return readAcademyAssist(academyAssistStorage.getAcademyAssistFilePath());
    }

    @Override
    public Optional<ReadOnlyAcademyAssist> readAcademyAssist(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return academyAssistStorage.readAcademyAssist(filePath);
    }

    @Override
    public void saveAcademyAssist(ReadOnlyAcademyAssist academyAssist) throws IOException {
        saveAcademyAssist(academyAssist, academyAssistStorage.getAcademyAssistFilePath());
    }

    @Override
    public void saveAcademyAssist(ReadOnlyAcademyAssist academyAssist, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        academyAssistStorage.saveAcademyAssist(academyAssist, filePath);
    }

}

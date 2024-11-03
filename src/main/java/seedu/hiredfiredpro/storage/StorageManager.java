package seedu.hiredfiredpro.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.hiredfiredpro.commons.core.LogsCenter;
import seedu.hiredfiredpro.commons.exceptions.DataLoadingException;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;
import seedu.hiredfiredpro.model.ReadOnlyUserPrefs;
import seedu.hiredfiredpro.model.UserPrefs;

/**
 * Manages storage of HiredFiredPro data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HiredFiredProStorage hiredFiredProStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code HiredFiredPro} and {@code UserPrefStorage}.
     */
    public StorageManager(HiredFiredProStorage hiredFiredProStorage, UserPrefsStorage userPrefsStorage) {
        this.hiredFiredProStorage = hiredFiredProStorage;
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


    // ================ HiredFiredPro methods ==============================

    @Override
    public Path getHiredFiredProFilePath() {
        return hiredFiredProStorage.getHiredFiredProFilePath();
    }

    @Override
    public Optional<ReadOnlyHiredFiredPro> readHiredFiredPro() throws DataLoadingException {
        return readHiredFiredPro(hiredFiredProStorage.getHiredFiredProFilePath());
    }

    @Override
    public Optional<ReadOnlyHiredFiredPro> readHiredFiredPro(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return hiredFiredProStorage.readHiredFiredPro(filePath);
    }

    @Override
    public void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro) throws IOException {
        saveHiredFiredPro(hiredFiredPro, hiredFiredProStorage.getHiredFiredProFilePath());
    }

    @Override
    public void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        hiredFiredProStorage.saveHiredFiredPro(hiredFiredPro, filePath);
    }

}

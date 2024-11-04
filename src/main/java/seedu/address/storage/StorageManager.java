package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPrudy;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Prudy data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PrudyStorage prudyStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code PrudyStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(PrudyStorage prudyStorage, UserPrefsStorage userPrefsStorage) {
        requireAllNonNull(prudyStorage, userPrefsStorage);
        this.prudyStorage = prudyStorage;
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


    // ================ Prudy methods ==============================

    @Override
    public Path getPrudyFilePath() {
        return prudyStorage.getPrudyFilePath();
    }

    @Override
    public Optional<ReadOnlyPrudy> readPrudy() throws DataLoadingException {
        return readPrudy(prudyStorage.getPrudyFilePath());
    }

    @Override
    public Optional<ReadOnlyPrudy> readPrudy(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return prudyStorage.readPrudy(filePath);
    }

    @Override
    public void savePrudy(ReadOnlyPrudy prudy) throws IOException {
        savePrudy(prudy, prudyStorage.getPrudyFilePath());
    }

    @Override
    public void savePrudy(ReadOnlyPrudy prudy, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        prudyStorage.savePrudy(prudy, filePath);
    }

}

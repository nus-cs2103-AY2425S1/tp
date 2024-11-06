package hallpointer.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import hallpointer.address.commons.core.LogsCenter;
import hallpointer.address.commons.exceptions.DataLoadingException;
import hallpointer.address.model.ReadOnlyHallPointer;
import hallpointer.address.model.ReadOnlyUserPrefs;
import hallpointer.address.model.UserPrefs;

/**
 * Manages storage of HallPointer data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HallPointerStorage hallPointerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code HallPointerStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(HallPointerStorage hallPointerStorage, UserPrefsStorage userPrefsStorage) {
        this.hallPointerStorage = hallPointerStorage;
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


    // ================ HallPointer methods ==============================

    @Override
    public Path getHallPointerFilePath() {
        return hallPointerStorage.getHallPointerFilePath();
    }

    @Override
    public Optional<ReadOnlyHallPointer> readHallPointer() throws DataLoadingException {
        return readHallPointer(hallPointerStorage.getHallPointerFilePath());
    }

    @Override
    public Optional<ReadOnlyHallPointer> readHallPointer(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return hallPointerStorage.readHallPointer(filePath);
    }

    @Override
    public void saveHallPointer(ReadOnlyHallPointer hallPointer) throws IOException {
        saveHallPointer(hallPointer, hallPointerStorage.getHallPointerFilePath());
    }

    @Override
    public void saveHallPointer(ReadOnlyHallPointer hallPointer, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        hallPointerStorage.saveHallPointer(hallPointer, filePath);
    }

}

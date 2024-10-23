package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyCampusConnect;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of CampusConnect data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CampusConnectStorage campusConnectStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code CampusConnectStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(CampusConnectStorage campusConnectStorage, UserPrefsStorage userPrefsStorage) {
        this.campusConnectStorage = campusConnectStorage;
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


    // ================ CampusConnect methods ==============================

    @Override
    public Path getCampusConnectFilePath() {
        return campusConnectStorage.getCampusConnectFilePath();
    }

    @Override
    public Optional<ReadOnlyCampusConnect> readCampusConnect() throws DataLoadingException {
        return readCampusConnect(campusConnectStorage.getCampusConnectFilePath());
    }

    @Override
    public Optional<ReadOnlyCampusConnect> readCampusConnect(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return campusConnectStorage.readCampusConnect(filePath);
    }

    @Override
    public void saveCampusConnect(ReadOnlyCampusConnect campusConnect) throws IOException {
        saveCampusConnect(campusConnect, campusConnectStorage.getCampusConnectFilePath());
    }

    @Override
    public void saveCampusConnect(ReadOnlyCampusConnect campusConnect, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        campusConnectStorage.saveCampusConnect(campusConnect, filePath);
    }

}

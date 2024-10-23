package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyClientHub;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ClientHub data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ClientHubStorage clientHubStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ClientHubStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ClientHubStorage clientHubStorage, UserPrefsStorage userPrefsStorage) {
        this.clientHubStorage = clientHubStorage;
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


    // ================ ClientHub methods ==============================

    @Override
    public Path getClientHubFilePath() {
        return clientHubStorage.getClientHubFilePath();
    }

    @Override
    public Optional<ReadOnlyClientHub> readClientHub() throws DataLoadingException {
        return readClientHub(clientHubStorage.getClientHubFilePath());
    }

    @Override
    public Optional<ReadOnlyClientHub> readClientHub(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return clientHubStorage.readClientHub(filePath);
    }

    @Override
    public void saveClientHub(ReadOnlyClientHub clientHub) throws IOException {
        saveClientHub(clientHub, clientHubStorage.getClientHubFilePath());
    }

    @Override
    public void saveClientHub(ReadOnlyClientHub clientHub, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        clientHubStorage.saveClientHub(clientHub, filePath);
    }

}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventTory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of EventTory data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EventToryStorage eventToryStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code EventToryStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(EventToryStorage eventToryStorage, UserPrefsStorage userPrefsStorage) {
        this.eventToryStorage = eventToryStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getEventToryFilePath() {
        return eventToryStorage.getEventToryFilePath();
    }

    @Override
    public Optional<ReadOnlyEventTory> readEventTory() throws DataLoadingException {
        return readEventTory(eventToryStorage.getEventToryFilePath());
    }

    @Override
    public Optional<ReadOnlyEventTory> readEventTory(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventToryStorage.readEventTory(filePath);
    }

    @Override
    public void saveEventTory(ReadOnlyEventTory eventTory) throws IOException {
        saveEventTory(eventTory, eventToryStorage.getEventToryFilePath());
    }

    @Override
    public void saveEventTory(ReadOnlyEventTory eventTory, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventToryStorage.saveEventTory(eventTory, filePath);
    }

}

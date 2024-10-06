package tutorease.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.ReadOnlyUserPrefs;
import tutorease.address.model.UserPrefs;

/**
 * Manages storage of TutorEase data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TutorEaseStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TutorEaseStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TutorEaseStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
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


    // ================ TutorEase methods ==============================

    @Override
    public Path getTutorEaseFilePath() {
        return addressBookStorage.getTutorEaseFilePath();
    }

    @Override
    public Optional<ReadOnlyTutorEase> readTutorEase() throws DataLoadingException {
        return readTutorEase(addressBookStorage.getTutorEaseFilePath());
    }

    @Override
    public Optional<ReadOnlyTutorEase> readTutorEase(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readTutorEase(filePath);
    }

    @Override
    public void saveTutorEase(ReadOnlyTutorEase addressBook) throws IOException {
        saveTutorEase(addressBook, addressBookStorage.getTutorEaseFilePath());
    }

    @Override
    public void saveTutorEase(ReadOnlyTutorEase addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveTutorEase(addressBook, filePath);
    }

}

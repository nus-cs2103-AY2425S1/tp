package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PawPatrolStorage pawPatrolStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code PawPatrolStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(PawPatrolStorage pawPatrolStorage, UserPrefsStorage userPrefsStorage) {
        this.pawPatrolStorage = pawPatrolStorage;
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
    public Path getPawPatrolFilePath() {
        return pawPatrolStorage.getPawPatrolFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readPawPatrol() throws DataLoadingException {
        return readPawPatrol(pawPatrolStorage.getPawPatrolFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readPawPatrol(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return pawPatrolStorage.readPawPatrol(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, pawPatrolStorage.getPawPatrolFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        pawPatrolStorage.saveAddressBook(addressBook, filePath);
    }

}

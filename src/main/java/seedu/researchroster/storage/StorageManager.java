package seedu.researchroster.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.researchroster.commons.core.LogsCenter;
import seedu.researchroster.commons.exceptions.DataLoadingException;
import seedu.researchroster.model.ReadOnlyResearchRoster;
import seedu.researchroster.model.ReadOnlyUserPrefs;
import seedu.researchroster.model.UserPrefs;

/**
 * Manages storage of ResearchRoster data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
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


    // ================ ResearchRoster methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyResearchRoster> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyResearchRoster> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyResearchRoster addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyResearchRoster addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

}

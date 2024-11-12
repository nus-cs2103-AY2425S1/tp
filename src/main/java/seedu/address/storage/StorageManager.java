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
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    // Path for the manual save/load file
    private Path manualSaveFilePath;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        manualSaveFilePath = addressBookStorage.getManualSaveFilePath();
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
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ Manual Save/Load Methods ==============================

    public Path getManualSaveFilePath() {
        return addressBookStorage.getManualSaveFilePath();
    }

    /**
     * Manually saves the address book to the specified file path.
     *
     * @param addressBook The address book data to save.
     * @throws IOException If there is an error saving the file.
     */
    @Override
    public void saveAddressBookManually(ReadOnlyAddressBook addressBook) throws IOException {
        if (manualSaveFilePath == null) {
            throw new IOException("Manual save file path is not set.");
        }
        logger.fine("Attempting to manually write to file: " + manualSaveFilePath);
        addressBookStorage.saveAddressBook(addressBook, manualSaveFilePath);
    }

    /**
     * Manually loads the address book from the specified file path.
     *
     * @return An optional containing the address book if successfully loaded, otherwise an empty optional.
     * @throws DataLoadingException If there is an error loading the file.
     */
    @Override
    public Optional<ReadOnlyAddressBook> loadAddressBookManually() throws DataLoadingException {

        logger.fine("Attempting to manually read from file: " + manualSaveFilePath);
        return addressBookStorage.readAddressBook(manualSaveFilePath);
    }

}

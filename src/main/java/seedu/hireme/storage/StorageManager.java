package seedu.hireme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.commons.exceptions.DataLoadingException;
import seedu.hireme.model.HireMeComparable;
import seedu.hireme.model.ReadOnlyAddressBook;
import seedu.hireme.model.ReadOnlyUserPrefs;
import seedu.hireme.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager<T extends HireMeComparable<T>> implements Storage<T> {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage<T> addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage<T> addressBookStorage, UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        logger.info("StorageManager initialized with AddressBookStorage and UserPrefsStorage.");
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        logger.fine("Fetching UserPrefs file path.");
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        logger.info("Reading UserPrefs from storage.");
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        logger.info("Saving UserPrefs to storage.");
        userPrefsStorage.saveUserPrefs(userPrefs);
        logger.fine("UserPrefs saved successfully.");
    }

    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        logger.fine("Fetching AddressBook file path.");
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook<T>> readAddressBook() throws DataLoadingException {
        logger.info("Reading AddressBook from default file path.");
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook<T>> readAddressBook(Path filePath) throws DataLoadingException {
        logger.info("Reading AddressBook from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook<T> addressBook) throws IOException {
        logger.info("Saving AddressBook to default file path.");
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
        logger.fine("AddressBook saved successfully.");
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook<T> addressBook, Path filePath) throws IOException {
        logger.info("Saving AddressBook to file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
        logger.fine("AddressBook saved successfully to file: " + filePath);
    }
}

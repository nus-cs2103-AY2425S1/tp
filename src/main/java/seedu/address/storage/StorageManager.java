package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.CommandTextHistory;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private CommandTextHistoryStorage commandTextHistoryStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage},
     * {@code CommandTextHistoryStorage}, and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, CommandTextHistoryStorage commandTextHistoryStorage,
            UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.commandTextHistoryStorage = commandTextHistoryStorage;
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

    // ================ CommandTextHistory methods ==============================

    @Override
    public Path getCommandTextHistoryFilePath() {
        return commandTextHistoryStorage.getCommandTextHistoryFilePath();
    }

    @Override
    public Optional<CommandTextHistory> readCommandTextHistory() throws DataLoadingException {
        return readCommandTextHistory(commandTextHistoryStorage.getCommandTextHistoryFilePath());
    }

    @Override
    public Optional<CommandTextHistory> readCommandTextHistory(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return commandTextHistoryStorage.readCommandTextHistory(filePath);
    }

    @Override
    public void saveCommandTextHistory(CommandTextHistory commandTextHistory) throws IOException {
        saveCommandTextHistory(commandTextHistory, commandTextHistoryStorage.getCommandTextHistoryFilePath());
    }

    @Override
    public void saveCommandTextHistory(CommandTextHistory commandTextHistory, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        commandTextHistoryStorage.saveCommandTextHistory(commandTextHistory, filePath);
    }

}

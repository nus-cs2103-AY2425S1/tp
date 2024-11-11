package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyClientBook;
import seedu.address.model.ReadOnlyMeetingBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private PropertyBookStorage propertyBookStorage;
    private ClientBookStorage clientBookStorage;
    private MeetingBookStorage meetingBookStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(UserPrefsStorage userPrefsStorage,
                          PropertyBookStorage propertyBookStorage, ClientBookStorage clientBookStorage,
                          MeetingBookStorage meetingBookStorage) {
        this.userPrefsStorage = userPrefsStorage;
        this.propertyBookStorage = propertyBookStorage;
        this.clientBookStorage = clientBookStorage;
        this.meetingBookStorage = meetingBookStorage;
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

    // ================ PropertyBook methods ==============================

    @Override
    public Path getPropertyBookFilePath() {
        return propertyBookStorage.getPropertyBookFilePath();
    }
    @Override
    public Optional<ReadOnlyPropertyBook> readPropertyBook() throws DataLoadingException {
        return readPropertyBook(propertyBookStorage.getPropertyBookFilePath());
    }
    @Override
    public Optional<ReadOnlyPropertyBook> readPropertyBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return propertyBookStorage.readPropertyBook(filePath);
    }
    @Override
    public void savePropertyBook(ReadOnlyPropertyBook propertyBook) throws IOException {
        savePropertyBook(propertyBook, propertyBookStorage.getPropertyBookFilePath());
    }
    @Override
    public void savePropertyBook(ReadOnlyPropertyBook propertyBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        propertyBookStorage.savePropertyBook(propertyBook, filePath);
    }

    // ================ ClientBook methods ==============================

    @Override
    public Path getClientBookFilePath() {
        return clientBookStorage.getClientBookFilePath();
    }

    @Override
    public Optional<ReadOnlyClientBook> readClientBook() throws DataLoadingException {
        return readClientBook(clientBookStorage.getClientBookFilePath());
    }

    @Override
    public Optional<ReadOnlyClientBook> readClientBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return clientBookStorage.readClientBook(filePath);
    }

    @Override
    public void saveClientBook(ReadOnlyClientBook clientBook) throws IOException {
        saveClientBook(clientBook, clientBookStorage.getClientBookFilePath());
    }

    @Override
    public void saveClientBook(ReadOnlyClientBook clientBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        clientBookStorage.saveClientBook(clientBook, filePath);
    }

    // ================ MeetingBook methods ==============================

    @Override
    public Path getMeetingBookFilePath() {
        return meetingBookStorage.getMeetingBookFilePath();
    }

    @Override
    public Optional<ReadOnlyMeetingBook> readMeetingBook() throws DataLoadingException {
        return readMeetingBook(meetingBookStorage.getMeetingBookFilePath());
    }

    @Override
    public Optional<ReadOnlyMeetingBook> readMeetingBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return meetingBookStorage.readMeetingBook(filePath);
    }

    @Override
    public void saveMeetingBook(ReadOnlyMeetingBook meetingBook) throws IOException {
        saveMeetingBook(meetingBook, meetingBookStorage.getMeetingBookFilePath());
    }

    @Override
    public void saveMeetingBook(ReadOnlyMeetingBook meetingBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        meetingBookStorage.saveMeetingBook(meetingBook, filePath);
    }
}

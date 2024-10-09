package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.LessonSchedule;
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
    private LessonScheduleStorage lessonScheduleStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          LessonScheduleStorage lessonScheduleStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.lessonScheduleStorage = lessonScheduleStorage;
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

    // ================ LessonSchedule methods ==============================
    /**
     * Returns the file path of the lesson schedule data file.
     */
    @Override
    public Path getLessonScheduleFilePath() {
        return lessonScheduleStorage.getLessonScheduleFilePath();
    }

    /**
     * Reads the lesson schedule from the default file path.
     * @param addressBook cannot be null.
     * @return an {@code Optional} containing the {@code LessonSchedule} if the file exists, otherwise an empty {@code
     * Optional}.
     * @throws DataLoadingException if there was any problem reading from the file.
     */
    @Override
    public Optional<LessonSchedule> readLessonSchedule(ReadOnlyAddressBook addressBook) throws DataLoadingException {
        return readLessonSchedule(lessonScheduleStorage.getLessonScheduleFilePath(), addressBook);
    }

    /**
     * Reads the lesson schedule from the specified file path.
     * @param filePath cannot be null.
     * @param addressBook cannot be null.
     * @return an {@code Optional} containing the {@code LessonSchedule} if the file exists, otherwise an empty {@code
     * Optional}.
     * @throws DataLoadingException if there was any problem reading from the file.
     */
    @Override
    public Optional<LessonSchedule> readLessonSchedule(Path filePath, ReadOnlyAddressBook addressBook) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return lessonScheduleStorage.readLessonSchedule(filePath, addressBook);
    }

    /**
     * Saves the given {@code LessonSchedule} to the storage.
     * @param lessonSchedule cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    public void saveLessonSchedule(LessonSchedule lessonSchedule) throws IOException {
        saveLessonSchedule(lessonSchedule, lessonScheduleStorage.getLessonScheduleFilePath());
    }

    /**
     * Saves the given {@code LessonSchedule} to the storage.
     * @param lessonSchedule cannot be null.
     * @param filePath cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    public void saveLessonSchedule(LessonSchedule lessonSchedule, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        lessonScheduleStorage.saveLessonSchedule(lessonSchedule, filePath);
    }
}

package tahub.contacts.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tahub.contacts.commons.core.LogsCenter;
import tahub.contacts.commons.exceptions.DataLoadingException;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.ReadOnlyUserPrefs;
import tahub.contacts.model.UserPrefs;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private JsonUniqueCourseListStorage courseListStorage;
    private JsonStudentCourseAssociationListStorage scaListStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          JsonUniqueCourseListStorage courseListStorage,
                          JsonStudentCourseAssociationListStorage scaListStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.courseListStorage = courseListStorage;
        this.scaListStorage = scaListStorage;
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

    //===== course list methods =====
    @Override
    public Path getCourseListFilePath() {
        return courseListStorage.getCourseListFilePath();
    }

    @Override
    public Optional<UniqueCourseList> readCourseList() throws DataLoadingException {
        return readCourseList(courseListStorage.getCourseListFilePath());
    }

    @Override
    public Optional<UniqueCourseList> readCourseList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return courseListStorage.readCourseList(filePath);
    }

    @Override
    public void saveCourseList(UniqueCourseList courseList) throws IOException {
        saveCourseList(courseList, courseListStorage.getCourseListFilePath());
    }

    @Override
    public void saveCourseList(UniqueCourseList courseList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        courseListStorage.saveCourseList(courseList, filePath);
    }

    //===== student course association list methods =====
    @Override
    public Path getScaListFilePath() {
        return scaListStorage.getScaListFilePath();
    }

    @Override
    public Optional<StudentCourseAssociationList> readScaList() throws DataLoadingException {
        return readScaList(scaListStorage.getScaListFilePath());
    }

    @Override
    public Optional<StudentCourseAssociationList> readScaList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return scaListStorage.readScaList(filePath);
    }

    @Override
    public void saveScaList(StudentCourseAssociationList scaList) throws IOException {
        saveScaList(scaList, scaListStorage.getScaListFilePath());
    }

    @Override
    public void saveScaList(StudentCourseAssociationList scaList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        scaListStorage.saveScaList(scaList, filePath);
    }
}

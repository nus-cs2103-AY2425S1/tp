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
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.tut.TutorialList;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final AddressBookStorage addressBookStorage;
    private final UserPrefsStorage userPrefsStorage;
    private final TutorialStorage tutorialStorage;
    private final AssignmentStorage assignmentStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage}, {@code UserPrefStorage}
     * and {@code AssignmentStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          AssignmentStorage assignmentStorage, TutorialStorage tutorialStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.assignmentStorage = assignmentStorage;
        this.tutorialStorage = tutorialStorage;
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

    // ================ Assignments methods ==============================
    @Override
    public Path getAssignmentFilePath() {
        return assignmentStorage.getAssignmentFilePath();
    }

    @Override
    public Optional<AssignmentList> readAssignments() throws DataLoadingException {
        return readAssignments(assignmentStorage.getAssignmentFilePath());
    }

    @Override
    public Optional<AssignmentList> readAssignments(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return assignmentStorage.readAssignments(filePath);
    }

    @Override
    public void saveAssignments(AssignmentList assignmentList) throws IOException {
        saveAssignments(assignmentList, assignmentStorage.getAssignmentFilePath());
    }

    @Override
    public void saveAssignments(AssignmentList assignmentList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        assignmentStorage.saveAssignments(assignmentList, filePath);
    }

    // ================ Tutorial methods ==============================
    @Override
    public Path getTutorialFilePath() {
        return tutorialStorage.getTutorialFilePath();
    }

    @Override
    public Optional<TutorialList> readTutorials() throws DataLoadingException {
        return readTutorials(tutorialStorage.getTutorialFilePath());
    }

    @Override
    public Optional<TutorialList> readTutorials(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read tutorial data from file: " + filePath);
        return tutorialStorage.readTutorials(filePath);
    }

    @Override
    public void saveTutorials(TutorialList tutorialList) throws IOException {
        saveTutorials(tutorialList, tutorialStorage.getTutorialFilePath());
    }

    @Override
    public void saveTutorials(TutorialList tutorialList, Path filePath) throws IOException {
        logger.fine("Attempting to write tutorial data to file: " + filePath);
        tutorialStorage.saveTutorials(tutorialList, filePath);
    }
}

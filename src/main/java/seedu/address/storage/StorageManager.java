package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyEduContacts;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of EduContacts data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EduContactsStorage eduContactsStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code EduContactsStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(EduContactsStorage eduContactsStorage, UserPrefsStorage userPrefsStorage) {
        this.eduContactsStorage = eduContactsStorage;
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


    // ================ EduContacts methods ==============================

    @Override
    public Path getEduContactsFilePath() {
        return eduContactsStorage.getEduContactsFilePath();
    }

    @Override
    public Optional<ReadOnlyEduContacts> readEduContacts() throws DataLoadingException {
        return readEduContacts(eduContactsStorage.getEduContactsFilePath());
    }

    @Override
    public Optional<ReadOnlyEduContacts> readEduContacts(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eduContactsStorage.readEduContacts(filePath);
    }

    @Override
    public void saveEduContacts(ReadOnlyEduContacts eduContacts) throws IOException {
        saveEduContacts(eduContacts, eduContactsStorage.getEduContactsFilePath());
    }

    @Override
    public void saveEduContacts(ReadOnlyEduContacts eduContacts, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eduContactsStorage.saveEduContacts(eduContacts, filePath);
    }

}

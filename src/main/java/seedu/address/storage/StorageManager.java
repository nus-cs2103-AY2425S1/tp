package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyClinicConnectSystem;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ClinicConnectSystem data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ClinicConnectSystemStorage clinicConnectSystemStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ClinicConnectSystemStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ClinicConnectSystemStorage clinicConnectSystemStorage, UserPrefsStorage userPrefsStorage) {
        this.clinicConnectSystemStorage = clinicConnectSystemStorage;
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


    // ================ ClinicConnectSystem methods ==============================

    @Override
    public Path getClinicConnectSystemFilePath() {
        return clinicConnectSystemStorage.getClinicConnectSystemFilePath();
    }

    @Override
    public Optional<ReadOnlyClinicConnectSystem> readClinicConnectSystem() throws DataLoadingException {
        return readClinicConnectSystem(clinicConnectSystemStorage.getClinicConnectSystemFilePath());
    }

    @Override
    public Optional<ReadOnlyClinicConnectSystem> readClinicConnectSystem(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return clinicConnectSystemStorage.readClinicConnectSystem(filePath);
    }

    @Override
    public void saveClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem) throws IOException {
        saveClinicConnectSystem(clinicConnectSystem, clinicConnectSystemStorage.getClinicConnectSystemFilePath());
    }

    @Override
    public void saveClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        clinicConnectSystemStorage.saveClinicConnectSystem(clinicConnectSystem, filePath);
    }

}

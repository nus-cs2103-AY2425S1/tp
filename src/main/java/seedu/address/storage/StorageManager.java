package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAgentAssist;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AgentAssist data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AgentAssistStorage agentAssistStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AgentAssistStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AgentAssistStorage agentAssistStorage, UserPrefsStorage userPrefsStorage) {
        this.agentAssistStorage = agentAssistStorage;
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


    // ================ AgentAssist methods ==============================

    @Override
    public Path getAgentAssistFilePath() {
        return agentAssistStorage.getAgentAssistFilePath();
    }

    @Override
    public Optional<ReadOnlyAgentAssist> readAgentAssist() throws DataLoadingException {
        return readAgentAssist(agentAssistStorage.getAgentAssistFilePath());
    }

    @Override
    public Optional<ReadOnlyAgentAssist> readAgentAssist(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return agentAssistStorage.readAgentAssist(filePath);
    }

    @Override
    public void saveAgentAssist(ReadOnlyAgentAssist addressBook) throws IOException {
        saveAgentAssist(addressBook, agentAssistStorage.getAgentAssistFilePath());
    }

    @Override
    public void saveAgentAssist(ReadOnlyAgentAssist addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        agentAssistStorage.saveAgentAssist(addressBook, filePath);
    }

}

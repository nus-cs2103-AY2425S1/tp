package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAgentAssist;

/**
 * A class to access AgentAssist data stored as a json file on the hard disk.
 */
public class JsonAgentAssistStorage implements AgentAssistStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAgentAssistStorage.class);

    private Path filePath;

    public JsonAgentAssistStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAgentAssistFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAgentAssist> readAgentAssist() throws DataLoadingException {
        return readAgentAssist(filePath);
    }

    /**
     * Similar to {@link #readAgentAssist()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyAgentAssist> readAgentAssist(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAgentAssist> jsonAgentAssist = JsonUtil.readJsonFile(
                filePath, JsonSerializableAgentAssist.class);
        if (!jsonAgentAssist.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAgentAssist.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAgentAssist(ReadOnlyAgentAssist addressBook) throws IOException {
        saveAgentAssist(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAgentAssist(ReadOnlyAgentAssist)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAgentAssist(ReadOnlyAgentAssist addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAgentAssist(addressBook), filePath);
    }

}

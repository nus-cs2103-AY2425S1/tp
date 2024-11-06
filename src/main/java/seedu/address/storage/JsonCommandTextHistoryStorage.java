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
import seedu.address.model.CommandTextHistory;

/**
 * A class to access CommandTextHistory data stored as a json file on the hard disk.
 */
public class JsonCommandTextHistoryStorage implements CommandTextHistoryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCommandTextHistoryStorage.class);

    private Path filePath;

    public JsonCommandTextHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCommandTextHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<CommandTextHistory> readCommandTextHistory() throws DataLoadingException {
        return readCommandTextHistory(filePath);
    }

    /**
     * Similar to {@link #readCommandTextHistory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<CommandTextHistory> readCommandTextHistory(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableCommandTextHistory> jsonCommandTextHistory = JsonUtil.readJsonFile(
                filePath, JsonSerializableCommandTextHistory.class);
        if (!jsonCommandTextHistory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCommandTextHistory.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveCommandTextHistory(CommandTextHistory commandTextHistory) throws IOException {
        saveCommandTextHistory(commandTextHistory, filePath);
    }

    /**
     * Similar to {@link #saveCommandTextHistory(CommandTextHistory)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCommandTextHistory(CommandTextHistory commandTextHistory, Path filePath) throws IOException {
        requireNonNull(commandTextHistory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCommandTextHistory(commandTextHistory), filePath);
    }

}

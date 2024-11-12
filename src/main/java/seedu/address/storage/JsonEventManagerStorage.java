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
import seedu.address.model.event.ReadOnlyEventManager;

/**
 * A class to access EventManager data stored as a json file on the hard disk.
 */
public class JsonEventManagerStorage implements EventManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEventManagerStorage.class);

    private Path filePath;

    public JsonEventManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getEventManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventManager> readEventManager() throws DataLoadingException {
        return readEventManager(filePath);
    }

    /**
     * Similar to {@link #readEventManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<ReadOnlyEventManager> readEventManager(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEventManager> jsonEventManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableEventManager.class);
        if (!jsonEventManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEventManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEventManager(ReadOnlyEventManager eventManager) throws IOException {
        saveEventManager(eventManager, filePath);
    }

    @Override
    public void saveEventManager(ReadOnlyEventManager eventManager, Path filePath) throws IOException {
        requireNonNull(eventManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEventManager(eventManager), filePath);
    }
}

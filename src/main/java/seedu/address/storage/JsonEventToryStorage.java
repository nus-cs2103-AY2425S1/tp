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
import seedu.address.model.ReadOnlyEventTory;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonEventToryStorage implements EventToryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEventToryStorage.class);

    private Path filePath;

    public JsonEventToryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEventToryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventTory> readEventTory() throws DataLoadingException {
        return readEventTory(filePath);
    }

    /**
     * Similar to {@link #readEventTory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyEventTory> readEventTory(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEventTory> jsonEventTory = JsonUtil.readJsonFile(
                filePath, JsonSerializableEventTory.class);
        if (!jsonEventTory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEventTory.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEventTory(ReadOnlyEventTory eventTory) throws IOException {
        saveEventTory(eventTory, filePath);
    }

    /**
     * Similar to {@link #saveEventTory(ReadOnlyEventTory)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEventTory(ReadOnlyEventTory eventTory, Path filePath) throws IOException {
        requireNonNull(eventTory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEventTory(eventTory), filePath);
    }

}

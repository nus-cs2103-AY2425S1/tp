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
import seedu.address.model.ReadOnlyClientHub;

/**
 * A class to access ClientHub data stored as a json file on the hard disk.
 */
public class JsonClientHubStorage implements ClientHubStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonClientHubStorage.class);

    private Path filePath;

    public JsonClientHubStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getClientHubFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyClientHub> readClientHub() throws DataLoadingException {
        return readClientHub(filePath);
    }

    /**
     * Similar to {@link #readClientHub()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyClientHub> readClientHub(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableClientHub> jsonClientHub = JsonUtil.readJsonFile(
                filePath, JsonSerializableClientHub.class);
        if (!jsonClientHub.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonClientHub.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveClientHub(ReadOnlyClientHub clientHub) throws IOException {
        saveClientHub(clientHub, filePath);
    }

    /**
     * Similar to {@link #saveClientHub(ReadOnlyClientHub)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveClientHub(ReadOnlyClientHub clientHub, Path filePath) throws IOException {
        requireNonNull(clientHub);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableClientHub(clientHub), filePath);
    }

}

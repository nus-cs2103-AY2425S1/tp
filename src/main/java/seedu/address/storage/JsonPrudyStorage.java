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
import seedu.address.model.ReadOnlyPrudy;

/**
 * A class to access Prudy data stored as a json file on the hard disk.
 */
public class JsonPrudyStorage implements PrudyStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPrudyStorage.class);

    private Path filePath;

    public JsonPrudyStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPrudyFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPrudy> readPrudy() throws DataLoadingException {
        return readPrudy(filePath);
    }

    /**
     * Similar to {@link #readPrudy()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyPrudy> readPrudy(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializablePrudy> jsonPrudy = JsonUtil.readJsonFile(
                filePath, JsonSerializablePrudy.class);
        if (!jsonPrudy.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPrudy.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void savePrudy(ReadOnlyPrudy prudy) throws IOException {
        savePrudy(prudy, filePath);
    }

    /**
     * Similar to {@link #savePrudy(ReadOnlyPrudy)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePrudy(ReadOnlyPrudy prudy, Path filePath) throws IOException {
        requireNonNull(prudy);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePrudy(prudy), filePath);
    }

}

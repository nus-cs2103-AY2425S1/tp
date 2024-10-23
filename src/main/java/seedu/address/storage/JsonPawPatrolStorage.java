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
import seedu.address.model.ReadOnlyPawPatrol;

/**
 * A class to access PawPatrol data stored as a json file on the hard disk.
 */
public class JsonPawPatrolStorage implements PawPatrolStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPawPatrolStorage.class);

    private Path filePath;

    public JsonPawPatrolStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPawPatrolFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPawPatrol> readPawPatrol() throws DataLoadingException {
        return readPawPatrol(filePath);
    }

    /**
     * Similar to {@link #readPawPatrol()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyPawPatrol> readPawPatrol(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializablePawPatrol> jsonPawPatrol = JsonUtil.readJsonFile(
            filePath, JsonSerializablePawPatrol.class);
        if (!jsonPawPatrol.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPawPatrol.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void savePawPatrol(ReadOnlyPawPatrol pawPatrol) throws IOException {
        savePawPatrol(pawPatrol, filePath);
    }

    /**
     * Similar to {@link #savePawPatrol(ReadOnlyPawPatrol)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePawPatrol(ReadOnlyPawPatrol pawPatrol, Path filePath) throws IOException {
        requireNonNull(pawPatrol);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePawPatrol(pawPatrol), filePath);
    }

}

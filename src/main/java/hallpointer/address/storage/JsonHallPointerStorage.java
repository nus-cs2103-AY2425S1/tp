package hallpointer.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import hallpointer.address.commons.core.LogsCenter;
import hallpointer.address.commons.exceptions.DataLoadingException;
import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.commons.util.FileUtil;
import hallpointer.address.commons.util.JsonUtil;
import hallpointer.address.model.ReadOnlyHallPointer;

/**
 * A class to access HallPointer data stored as a json file on the hard disk.
 */
public class JsonHallPointerStorage implements HallPointerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHallPointerStorage.class);

    private Path filePath;

    public JsonHallPointerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHallPointerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHallPointer> readHallPointer() throws DataLoadingException {
        return readHallPointer(filePath);
    }

    /**
     * Similar to {@link #readHallPointer()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyHallPointer> readHallPointer(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableHallPointer> jsonSerializableHallPointer = JsonUtil.readJsonFile(
                filePath, JsonSerializableHallPointer.class);
        if (!jsonSerializableHallPointer.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSerializableHallPointer.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveHallPointer(ReadOnlyHallPointer hallPointer) throws IOException {
        saveHallPointer(hallPointer, filePath);
    }

    /**
     * Similar to {@link #saveHallPointer(ReadOnlyHallPointer)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHallPointer(ReadOnlyHallPointer hallPointer, Path filePath) throws IOException {
        requireNonNull(hallPointer);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHallPointer(hallPointer), filePath);
    }

}

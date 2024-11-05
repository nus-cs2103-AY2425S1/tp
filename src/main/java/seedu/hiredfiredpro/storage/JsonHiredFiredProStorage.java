package seedu.hiredfiredpro.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.hiredfiredpro.commons.core.LogsCenter;
import seedu.hiredfiredpro.commons.exceptions.DataLoadingException;
import seedu.hiredfiredpro.commons.exceptions.IllegalValueException;
import seedu.hiredfiredpro.commons.util.FileUtil;
import seedu.hiredfiredpro.commons.util.JsonUtil;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;

/**
 * A class to access HiredFiredPro data stored as a json file on the hard disk.
 */
public class JsonHiredFiredProStorage implements HiredFiredProStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHiredFiredProStorage.class);

    private Path filePath;

    public JsonHiredFiredProStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHiredFiredProFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHiredFiredPro> readHiredFiredPro() throws DataLoadingException {
        return readHiredFiredPro(filePath);
    }

    /**
     * Similar to {@link #readHiredFiredPro()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyHiredFiredPro> readHiredFiredPro(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableHiredFiredPro> jsonHiredFiredPro = JsonUtil.readJsonFile(
                filePath, JsonSerializableHiredFiredPro.class);
        if (!jsonHiredFiredPro.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHiredFiredPro.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro) throws IOException {
        saveHiredFiredPro(hiredFiredPro, filePath);
    }

    /**
     * Similar to {@link #saveHiredFiredPro(ReadOnlyHiredFiredPro)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro, Path filePath) throws IOException {
        requireNonNull(hiredFiredPro);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHiredFiredPro(hiredFiredPro), filePath);
    }

}

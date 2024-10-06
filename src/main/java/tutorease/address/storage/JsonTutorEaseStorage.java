package tutorease.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.commons.exceptions.IllegalValueException;
import tutorease.address.commons.util.FileUtil;
import tutorease.address.commons.util.JsonUtil;
import tutorease.address.model.ReadOnlyTutorEase;

/**
 * A class to access TutorEase data stored as a json file on the hard disk.
 */
public class JsonTutorEaseStorage implements TutorEaseStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTutorEaseStorage.class);

    private Path filePath;

    public JsonTutorEaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTutorEaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTutorEase> readTutorEase() throws DataLoadingException {
        return readTutorEase(filePath);
    }

    /**
     * Similar to {@link #readTutorEase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTutorEase> readTutorEase(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTutorEase> jsonTutorEase = JsonUtil.readJsonFile(
                filePath, JsonSerializableTutorEase.class);
        if (!jsonTutorEase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTutorEase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTutorEase(ReadOnlyTutorEase addressBook) throws IOException {
        saveTutorEase(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveTutorEase(ReadOnlyTutorEase)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTutorEase(ReadOnlyTutorEase addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTutorEase(addressBook), filePath);
    }

}

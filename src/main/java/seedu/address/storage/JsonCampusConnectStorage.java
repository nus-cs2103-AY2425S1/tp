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
import seedu.address.model.ReadOnlyCampusConnect;

/**
 * A class to access CampusConnect data stored as a json file on the hard disk.
 */
public class JsonCampusConnectStorage implements CampusConnectStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCampusConnectStorage.class);

    private Path filePath;

    public JsonCampusConnectStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCampusConnectFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCampusConnect> readCampusConnect() throws DataLoadingException {
        return readCampusConnect(filePath);
    }

    /**
     * Similar to {@link #readCampusConnect()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyCampusConnect> readCampusConnect(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableCampusConnect> jsonCampusConnect = JsonUtil.readJsonFile(
                filePath, JsonSerializableCampusConnect.class);
        if (!jsonCampusConnect.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCampusConnect.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveCampusConnect(ReadOnlyCampusConnect campusConnect) throws IOException {
        saveCampusConnect(campusConnect, filePath);
    }

    /**
     * Similar to {@link #saveCampusConnect(ReadOnlyCampusConnect)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCampusConnect(ReadOnlyCampusConnect campusConnect, Path filePath) throws IOException {
        requireNonNull(campusConnect);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCampusConnect(campusConnect), filePath);
    }

}

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
import seedu.address.model.ReadOnlyClinicConnectSystem;

/**
 * A class to access ClinicConnectSystem data stored as a json file on the hard disk.
 */
public class JsonClinicConnectSystemStorage implements ClinicConnectSystemStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonClinicConnectSystemStorage.class);

    private Path filePath;

    public JsonClinicConnectSystemStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getClinicConnectSystemFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyClinicConnectSystem> readClinicConnectSystem() throws DataLoadingException {
        return readClinicConnectSystem(filePath);
    }

    /**
     * Similar to {@link #readClinicConnectSystem()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyClinicConnectSystem> readClinicConnectSystem(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableClinicConnectSystem> jsonClinicConnectSystem = JsonUtil.readJsonFile(
                filePath, JsonSerializableClinicConnectSystem.class);
        if (!jsonClinicConnectSystem.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonClinicConnectSystem.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem) throws IOException {
        saveClinicConnectSystem(clinicConnectSystem, filePath);
    }

    /**
     * Similar to {@link #saveClinicConnectSystem(ReadOnlyClinicConnectSystem)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem, Path filePath)
            throws IOException {
        requireNonNull(clinicConnectSystem);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableClinicConnectSystem(clinicConnectSystem), filePath);
    }

}

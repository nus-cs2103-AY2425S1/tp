package seedu.address.storage.property;

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
import seedu.address.model.ReadOnlyPropertyList;

/**
 * A class to access PropertyList data stored as a json file on the hard disk.
 */
public class JsonPropertyListStorage implements PropertyListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPropertyListStorage.class);

    private Path filePath;

    public JsonPropertyListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPropertyListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPropertyList> readPropertyList() throws DataLoadingException {
        return readPropertyList(filePath);
    }

    /**
     * Similar to {@link #readPropertyList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyPropertyList> readPropertyList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializablePropertyList> jsonPropertyList = JsonUtil.readJsonFile(
                filePath, JsonSerializablePropertyList.class);
        if (!jsonPropertyList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPropertyList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void savePropertyList(ReadOnlyPropertyList propertyList) throws IOException {
        savePropertyList(propertyList, filePath);
    }

    /**
     * Similar to {@link #savePropertyList(ReadOnlyPropertyList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePropertyList(ReadOnlyPropertyList propertyList, Path filePath) throws IOException {
        requireNonNull(propertyList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        logger.info("saving property list");
        JsonUtil.saveJsonFile(new JsonSerializablePropertyList(propertyList), filePath);
    }

}

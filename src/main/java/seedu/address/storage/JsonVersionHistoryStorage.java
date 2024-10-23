package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.VersionHistory;

/**
 * A class to access VersionHistory stored in the hard disk as a json file
 */
public class JsonVersionHistoryStorage implements VersionHistoryStorage {

    private Path filePath;

    public JsonVersionHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getVersionHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<VersionHistory> readVersionHistory() throws DataLoadingException {
        return readVersionHistory(filePath);
    }

    /**
     * Similar to {@link #readVersionHistory()}
     * @param historyFilePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    public Optional<VersionHistory> readVersionHistory(Path historyFilePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableVersionHistory> jsonVersionHistory = JsonUtil.readJsonFile(
                filePath, JsonSerializableVersionHistory.class);
        if (!jsonVersionHistory.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonVersionHistory.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveVersionHistory(VersionHistory versionHistory) throws IOException {
        JsonUtil.saveJsonFile(new JsonSerializableVersionHistory(versionHistory), filePath);
    }

}

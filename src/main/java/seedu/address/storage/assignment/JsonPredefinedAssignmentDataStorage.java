package seedu.address.storage.assignment;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.assignment.ReadOnlyPredefinedAssignmentsData;

/**
 * A class to access predefined assignments
 * stored as a json file on the hard disk.
 */
public class JsonPredefinedAssignmentDataStorage implements PredefinedAssignmentDataStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonPredefinedAssignmentDataStorage.class);
    private Path filePath;

    public JsonPredefinedAssignmentDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAssignmentFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPredefinedAssignmentsData> readAssignment() throws DataLoadingException {
        return readAssignment(filePath);
    }

    @Override
    public Optional<ReadOnlyPredefinedAssignmentsData> readAssignment(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        Optional<JsonSerializablePredefinedAssignmentData> jsonPredefinedAssignment = JsonUtil.readJsonFile(
                filePath, JsonSerializablePredefinedAssignmentData.class);
        if (jsonPredefinedAssignment.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonPredefinedAssignment.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

}

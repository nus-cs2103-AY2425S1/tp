package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.assignment.AssignmentList;

/**
 * A class to access Assignment data stored as a json file on the hard disk.
 */
public class JsonAssignmentStorage implements AssignmentStorage {
    private final Path filePath;

    public JsonAssignmentStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAssignmentFilePath() {
        return filePath;
    }

    @Override
    public Optional<AssignmentList> readAssignments() throws DataLoadingException {
        return readAssignments(filePath);
    }

    @Override
    public Optional<AssignmentList> readAssignments(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAssignmentList> jsonAssignmentList = JsonUtil.readJsonFile(
                filePath, JsonSerializableAssignmentList.class);
        if (!jsonAssignmentList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAssignmentList.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAssignments(AssignmentList assignmentList) throws IOException {
        saveAssignments(assignmentList, filePath);
    }

    @Override
    public void saveAssignments(AssignmentList assignmentList, Path filePath) throws IOException {
        JsonUtil.saveJsonFile(new JsonSerializableAssignmentList(assignmentList), filePath);
    }
}

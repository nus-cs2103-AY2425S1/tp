package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.tut.TutorialList;

/**
 * A class to access Tutorial data stored as a JSON file on the hard disk.
 */
public class JsonTutorialStorage implements TutorialStorage {
    private final Path filePath;

    public JsonTutorialStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTutorialFilePath() {
        return filePath;
    }

    @Override
    public Optional<TutorialList> readTutorials() throws DataLoadingException {
        return readTutorials(filePath);
    }

    @Override
    public Optional<TutorialList> readTutorials(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTutorialList> jsonSerializableTutorialList = JsonUtil.readJsonFile(filePath,
                JsonSerializableTutorialList.class);
        if (jsonSerializableTutorialList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSerializableTutorialList.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTutorials(TutorialList tutorialList) throws IOException {
        saveTutorials(tutorialList, filePath);
    }

    @Override
    public void saveTutorials(TutorialList tutorialList, Path filePath) throws IOException {
        requireNonNull(tutorialList);
        requireNonNull(filePath);

        JsonUtil.saveJsonFile(new JsonSerializableTutorialList(tutorialList), filePath);
    }
}

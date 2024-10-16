package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.tut.Tut;

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
    public Optional<List<Tut>> readTutorials() throws DataLoadingException {
        return readTutorials(filePath);
    }

    @Override
    public Optional<List<Tut>> readTutorials(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonAdaptedTut[]> jsonAdaptedTutorials = JsonUtil.readJsonFile(filePath, JsonAdaptedTut[].class);
        if (!jsonAdaptedTutorials.isPresent()) {
            return Optional.empty();
        }

        try {
            List<Tut> tutorials = new ArrayList<>();
            for (JsonAdaptedTut jsonAdaptedTut : jsonAdaptedTutorials.get()) {
                tutorials.add(jsonAdaptedTut.toModelType());
            }
            return Optional.of(tutorials);
        } catch (IllegalValueException ive) {
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTutorials(List<Tut> tutorialList) throws IOException {
        saveTutorials(tutorialList, filePath);
    }

    @Override
    public void saveTutorials(List<Tut> tutorialList, Path filePath) throws IOException {
        requireNonNull(tutorialList);
        requireNonNull(filePath);

        List<JsonAdaptedTut> jsonAdaptedTutList = tutorialList.stream()
                .map(JsonAdaptedTut::new)
                .collect(Collectors.toList());

        JsonUtil.saveJsonFile(jsonAdaptedTutList.toArray(new JsonAdaptedTut[0]), filePath);
    }
}

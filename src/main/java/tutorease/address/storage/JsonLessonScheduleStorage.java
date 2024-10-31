package tutorease.address.storage;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.commons.exceptions.IllegalValueException;
import tutorease.address.commons.util.FileUtil;
import tutorease.address.commons.util.JsonUtil;
import tutorease.address.model.ReadOnlyLessonSchedule;
import tutorease.address.model.ReadOnlyTutorEase;

/**
 * A class to access LessonSchedule data stored as a json file on the hard disk.
 */
public class JsonLessonScheduleStorage implements LessonScheduleStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonLessonScheduleStorage.class);
    private Path filePath;

    public JsonLessonScheduleStorage(Path filePath) {
        this.filePath = filePath;
    }
    @Override
    public Path getLessonScheduleFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLessonSchedule> readLessonSchedule(ReadOnlyTutorEase tutorEase)
            throws DataLoadingException {
        return readLessonSchedule(filePath, tutorEase);
    }

    @Override
    public Optional<ReadOnlyLessonSchedule> readLessonSchedule(Path filePath, ReadOnlyTutorEase tutorEase) throws
            DataLoadingException {
        requireAllNonNull(filePath);

        Optional<JsonSerializableLessonSchedule> jsonLessonSchedule = JsonUtil.readJsonFile(
                filePath, JsonSerializableLessonSchedule.class);
        if (!jsonLessonSchedule.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLessonSchedule.get().toModelType(tutorEase));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule) throws IOException {
        saveLessonSchedule(lessonSchedule, filePath);
    }

    @Override
    public void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule, Path filePath) throws IOException {
        requireNonNull(lessonSchedule);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLessonSchedule(lessonSchedule), filePath);
    }
}

package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.LessonSchedule;
import seedu.address.model.ReadOnlyAddressBook;

public class JsonLessonScheduleStorage implements LessonScheduleStorage {
    private Path filePath;

    public JsonLessonScheduleStorage(Path filePath) {
        this.filePath = filePath;
    }
    @Override
    public Path getLessonScheduleFilePath() {
        return filePath;
    }

    @Override
    public Optional<LessonSchedule> readLessonSchedule(ReadOnlyAddressBook addressBook) throws DataLoadingException {
        return readLessonSchedule(filePath, addressBook);
    }

    @Override
    public Optional<LessonSchedule> readLessonSchedule(Path filePath, ReadOnlyAddressBook addressBook) throws DataLoadingException {
        requireAllNonNull(filePath);

        Optional<JsonSerializableLessonSchedule> jsonLessonSchedule = JsonUtil.readJsonFile(
                filePath, JsonSerializableLessonSchedule.class);
        if (!jsonLessonSchedule.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLessonSchedule.get().toModelType(addressBook));
        } catch (IllegalValueException ive) {
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveLessonSchedule(LessonSchedule lessonSchedule) throws IOException {
        saveLessonSchedule(lessonSchedule, filePath);
    }

    @Override
    public void saveLessonSchedule(LessonSchedule lessonSchedule, Path filePath) throws IOException {
        requireNonNull(lessonSchedule);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLessonSchedule(lessonSchedule), filePath);
    }
}

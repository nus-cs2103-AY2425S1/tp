package tutorease.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.ReadOnlyLessonSchedule;
import tutorease.address.model.ReadOnlyTutorEase;

/**
 * Represents a storage for {@link LessonSchedule}.
 */
public interface LessonScheduleStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getLessonScheduleFilePath();

    /**
     * Returns LessonSchedule data as a {@link ReadOnlyLessonSchedule}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyLessonSchedule> readLessonSchedule(ReadOnlyTutorEase tutorEase) throws DataLoadingException;

    /**
     * @see #getLessonScheduleFilePath()
     */
    Optional<ReadOnlyLessonSchedule> readLessonSchedule(Path filePath, ReadOnlyTutorEase tutorEase) throws
            DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyLessonSchedule} to the storage.
     * @param lessonSchedule cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule) throws IOException;

    /**
     * @see #saveLessonSchedule(ReadOnlyLessonSchedule)
     */
    void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule, Path filePath) throws IOException;

}

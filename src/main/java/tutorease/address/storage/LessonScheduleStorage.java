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
     * @param tutorEase The TutorEase object to use for reading the lesson schedule.
     * @return an {@link Optional} containing the lesson schedule, or {@link Optional#empty()} if the storage file is
     * @throws DataLoadingException If loading the data from storage failed.
     */
    Optional<ReadOnlyLessonSchedule> readLessonSchedule(ReadOnlyTutorEase tutorEase) throws DataLoadingException;

    /**
     * Reads the lesson schedule from the specified file path.
     *
     * @param filePath The file path to read.
     * @param tutorEase The tutorease to read.
     * @return An {@code Optional} containing the {@code LessonSchedule} if the file exists, otherwise an empty {@code
     * Optional}.
     * @throws DataLoadingException If there was any problem reading from the file.
     */
    Optional<ReadOnlyLessonSchedule> readLessonSchedule(Path filePath, ReadOnlyTutorEase tutorEase) throws
            DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyLessonSchedule} to the storage.
     *
     * @param lessonSchedule The lesson schedule to save.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule) throws IOException;

    /**
     * Saves the given {@link ReadOnlyLessonSchedule} to the storage.
     * @param lessonSchedule The lesson schedule to save.
     * @param filePath The file path to save the lesson schedule.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule, Path filePath) throws IOException;

}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.LessonSchedule;
import seedu.address.model.ReadOnlyAddressBook;

public interface LessonScheduleStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getLessonScheduleFilePath();

    /**
     * Returns LessonSchedule data as a {@link LessonSchedule}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<LessonSchedule> readLessonSchedule(ReadOnlyAddressBook addressBook) throws DataLoadingException;

    /**
     * @see #getLessonScheduleFilePath()
     */
    Optional<LessonSchedule> readLessonSchedule(Path filePath, ReadOnlyAddressBook addressBook) throws DataLoadingException;

    /**
     * Saves the given {@link LessonSchedule} to the storage.
     * @param lessonSchedule cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLessonSchedule(LessonSchedule lessonSchedule) throws IOException;

    /**
     * @see #saveLessonSchedule(LessonSchedule)
     */
    void saveLessonSchedule(LessonSchedule lessonSchedule, Path filePath) throws IOException;

}

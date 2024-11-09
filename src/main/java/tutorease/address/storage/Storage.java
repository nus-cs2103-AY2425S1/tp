package tutorease.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.model.ReadOnlyLessonSchedule;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.ReadOnlyUserPrefs;
import tutorease.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TutorEaseStorage, UserPrefsStorage, LessonScheduleStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTutorEaseFilePath();

    @Override
    Optional<ReadOnlyTutorEase> readTutorEase() throws DataLoadingException;

    @Override
    void saveTutorEase(ReadOnlyTutorEase tutorEase) throws IOException;

    @Override
    Path getLessonScheduleFilePath();

    @Override
    Optional<ReadOnlyLessonSchedule> readLessonSchedule(ReadOnlyTutorEase tutorEase) throws DataLoadingException;

    @Override
    void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule) throws IOException;
}

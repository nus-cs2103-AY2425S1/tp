package tutorease.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.model.ReadOnlyLessonSchedule;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.ReadOnlyUserPrefs;
import tutorease.address.model.UserPrefs;

/**
 * Manages storage of TutorEase data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final TutorEaseStorage tutorEaseStorage;
    private final UserPrefsStorage userPrefsStorage;
    private final LessonScheduleStorage lessonScheduleStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TutorEaseStorage}, {@code UserPrefsStorage} and
     * {@code LessonScheduleStorage}.
     *
     * @param tutorEaseStorage the storage for TutorEase data.
     * @param userPrefsStorage the storage for user preferences data.
     * @param lessonScheduleStorage the storage for lesson schedule data.
     */
    public StorageManager(TutorEaseStorage tutorEaseStorage, UserPrefsStorage userPrefsStorage,
                          LessonScheduleStorage lessonScheduleStorage) {
        this.tutorEaseStorage = tutorEaseStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.lessonScheduleStorage = lessonScheduleStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TutorEase methods ==============================

    @Override
    public Path getTutorEaseFilePath() {
        return tutorEaseStorage.getTutorEaseFilePath();
    }

    @Override
    public Optional<ReadOnlyTutorEase> readTutorEase() throws DataLoadingException {
        return readTutorEase(tutorEaseStorage.getTutorEaseFilePath());
    }

    @Override
    public Optional<ReadOnlyTutorEase> readTutorEase(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tutorEaseStorage.readTutorEase(filePath);
    }

    @Override
    public void saveTutorEase(ReadOnlyTutorEase addressBook) throws IOException {
        saveTutorEase(addressBook, tutorEaseStorage.getTutorEaseFilePath());
    }

    @Override
    public void saveTutorEase(ReadOnlyTutorEase addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tutorEaseStorage.saveTutorEase(addressBook, filePath);
    }

    // ================ LessonSchedule methods ==============================
    @Override
    public Path getLessonScheduleFilePath() {
        return lessonScheduleStorage.getLessonScheduleFilePath();
    }

    @Override
    public Optional<ReadOnlyLessonSchedule> readLessonSchedule(ReadOnlyTutorEase tutorEase)
            throws DataLoadingException {
        return readLessonSchedule(lessonScheduleStorage.getLessonScheduleFilePath(), tutorEase);
    }

    @Override
    public Optional<ReadOnlyLessonSchedule> readLessonSchedule(Path filePath, ReadOnlyTutorEase tutorEase) throws
            DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return lessonScheduleStorage.readLessonSchedule(filePath, tutorEase);
    }

    @Override
    public void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule) throws IOException {
        saveLessonSchedule(lessonSchedule, lessonScheduleStorage.getLessonScheduleFilePath());
    }

    @Override
    public void saveLessonSchedule(ReadOnlyLessonSchedule lessonSchedule, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        lessonScheduleStorage.saveLessonSchedule(lessonSchedule, filePath);
    }
}

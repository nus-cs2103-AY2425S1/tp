package tutorease.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalStudents.ALICE;
import static tutorease.address.testutil.TypicalStudents.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tutorease.address.commons.core.GuiSettings;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.NameContainsKeywordsPredicate;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;
import tutorease.address.testutil.StudentBuilder;
import tutorease.address.testutil.TutorEaseBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TutorEase(), new TutorEase(modelManager.getTutorEase()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTutorEaseFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTutorEaseFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTutorEaseFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setTutorEaseFilePath(null));
    }

    @Test
    public void setTutorEaseFilePath_validPath_setsTutorEaseFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setTutorEaseFilePath(path);
        assertEquals(path, modelManager.getTutorEaseFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInTutorEase_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInTutorEase_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void deleteLesson_success() throws ParseException {
        LessonSchedule lessonSchedule = modelManager.getLessonSchedule();
        LessonBuilder lessonBuilder = new LessonBuilder();
        Lesson lesson = lessonBuilder.build();
        lessonSchedule.addLesson(lesson);
        assertTrue(lessonSchedule.hasLesson(lesson));
        lessonSchedule.deleteLesson(lesson);
        assertFalse(lessonSchedule.hasLesson(lesson));
    }

    @Test
    public void getLesson_success() throws ParseException {
        LessonSchedule lessonSchedule = modelManager.getLessonSchedule();
        LessonBuilder lessonBuilder = new LessonBuilder();
        Lesson lesson = lessonBuilder.build();
        lessonSchedule.addLesson(lesson);
        assertTrue(lessonSchedule.hasLesson(lesson));
        assertEquals(lesson, lessonSchedule.getLesson(0));
    }

    @Test
    public void getLessonScheduleSize_success() throws ParseException {
        LessonSchedule lessonSchedule = modelManager.getLessonSchedule();
        LessonBuilder lessonBuilder = new LessonBuilder();
        Lesson lesson = lessonBuilder.build();
        assertEquals(0, modelManager.getLessonScheduleSize());
        lessonSchedule.addLesson(lesson);
        assertEquals(1, modelManager.getLessonScheduleSize());
    }

    @Test
    public void equals() {
        TutorEase tutorEase = new TutorEaseBuilder().withPerson(ALICE).withPerson(BENSON).build();
        TutorEase differentTutorEase = new TutorEase();
        UserPrefs userPrefs = new UserPrefs();
        LessonSchedule lessonSchedule = new LessonSchedule();

        // same values -> returns true
        modelManager = new ModelManager(tutorEase, userPrefs, lessonSchedule);
        ModelManager modelManagerCopy = new ModelManager(tutorEase, userPrefs, lessonSchedule);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different TutorEase -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTutorEase, userPrefs, lessonSchedule)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(tutorEase, userPrefs, lessonSchedule)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTutorEaseFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(tutorEase, differentUserPrefs, lessonSchedule)));
    }

    @Test
    public void deleteStudentLesson_oneStudent_success() throws ParseException {
        LessonSchedule lessonSchedule = modelManager.getLessonSchedule();
        LessonBuilder lessonBuilder = new LessonBuilder();
        Lesson lesson = lessonBuilder.build();
        Person student = lesson.getStudent();
        lessonSchedule.addLesson(lesson);
        assertTrue(lessonSchedule.hasLesson(lesson));
        modelManager.deleteStudentLesson(student);
        assertFalse(lessonSchedule.hasLesson(lesson));
    }

    @Test
    public void deleteStudentLesson_noStudent_success() throws ParseException {
        LessonSchedule lessonSchedule = modelManager.getLessonSchedule();
        LessonBuilder lessonBuilder = new LessonBuilder();
        Lesson lesson = lessonBuilder.build();
        Person student = lesson.getStudent();
        assertEquals(0, lessonSchedule.getSize());
        modelManager.deleteStudentLesson(student);
        assertEquals(0, lessonSchedule.getSize());
    }

    @Test
    public void deleteStudentLesson_multipleStudent_success() throws ParseException {
        LessonSchedule lessonSchedule = modelManager.getLessonSchedule();

        // Create Alice's Lesson
        LessonBuilder lessonBuilderAlice = new LessonBuilder();
        Lesson lessonAlice = lessonBuilderAlice.build();

        // Create Bob
        StudentBuilder studentBuilder = new StudentBuilder().withName("Bob");
        Person bob = studentBuilder.build();

        // Create Bob's Lesson
        LessonBuilder lessonBuilderBob = new LessonBuilder().withName(bob)
                .withStartDateTime("02-02-2024 " + "12:00")
                .withEndDateTime("02-02-2024 " + "13:00");
        Lesson lessonBob = lessonBuilderBob.build();
        assertEquals(bob, lessonBob.getStudent());

        // Add both Alice's and Bob's lesson
        lessonSchedule.addLesson(lessonAlice);
        lessonSchedule.addLesson(lessonBob);
        assertEquals(2, lessonSchedule.getSize());
        assertTrue(lessonSchedule.hasLesson(lessonAlice));
        assertTrue(lessonSchedule.hasLesson(lessonBob));

        // Only remove Bob's lesson so only Alice's remain
        modelManager.deleteStudentLesson(bob);
        assertEquals(1, lessonSchedule.getSize());
        assertTrue(lessonSchedule.hasLesson(lessonAlice));
        assertFalse(lessonSchedule.hasLesson(lessonBob));
    }
}

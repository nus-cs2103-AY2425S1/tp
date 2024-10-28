package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONSULTATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalConsultations.CONSULT_1;
import static seedu.address.testutil.TypicalLessons.LESSON_1;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.LessonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
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
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInAddressBook_returnsTrue() {
        modelManager.addStudent(ALICE);
        assertTrue(modelManager.hasStudent(ALICE));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(0));
    }

    @Test
    public void hasConsultation_nullConsultation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasConsult(null));
    }

    @Test
    public void hasConsultation_consultationNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasConsult(CONSULT_1));
    }

    @Test
    public void hasConsultation_consultationInAddressBook_returnsTrue() {
        modelManager.addConsult(CONSULT_1);
        assertTrue(modelManager.hasConsult(CONSULT_1));
    }

    @Test
    public void getFilteredConsultationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredConsultationList().remove(0));
    }

    @Test
    public void hasLesson_lessonNotInAddressBook_returnsFalse() {
        Lesson lesson = new LessonBuilder().withDate("2024-11-01").withTime("10:00").build();
        assertFalse(modelManager.hasLesson(lesson));
    }

    @Test
    public void hasLesson_lessonInAddressBook_returnsTrue() {
        Lesson lesson = new LessonBuilder().withDate("2024-11-01").withTime("10:00").build();
        modelManager.addLesson(lesson);
        assertTrue(modelManager.hasLesson(lesson));
    }

    @Test
    public void addLesson_addsLessonSuccessfully() {
        Lesson lesson = new LessonBuilder().withDate("2024-11-01").withTime("10:00").build();
        modelManager.addLesson(lesson);
        assertTrue(modelManager.hasLesson(lesson));
    }

    @Test
    public void deleteLesson_deletesLessonSuccessfully() {
        // Arrange
        Lesson lesson = new LessonBuilder().withDate("2024-11-01").withTime("10:00").build();
        modelManager.addLesson(lesson);
        assertTrue(modelManager.hasLesson(lesson)); // Ensure the lesson was added

        // Act
        modelManager.deleteLesson(lesson);

        // Assert
        assertFalse(modelManager.hasLesson(lesson)); // Ensure the lesson was deleted
    }

    @Test
    public void setLesson_setsLessonSuccessfully() {
        // Arrange
        Lesson lesson = new LessonBuilder().withDate("2024-11-01").withTime("10:00").build();
        modelManager.addLesson(lesson);
        assertTrue(modelManager.hasLesson(lesson)); // Ensure the lesson was added

        // Act
        Lesson otherLesson = new LessonBuilder().withDate("2024-11-02").withTime("11:00").build();
        modelManager.setLesson(lesson, otherLesson);

        // Assert
        assertFalse(modelManager.hasLesson(lesson)); // Ensure the lesson was edited
        assertTrue(modelManager.hasLesson(otherLesson)); // Ensure the lesson is edited
    }


    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder()
                .withStudent(ALICE).withStudent(BENSON)
                .withConsultation(CONSULT_1)
                .withLesson(LESSON_1).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        modelManager.updateFilteredConsultationList(PREDICATE_SHOW_ALL_CONSULTATIONS);
        modelManager.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}

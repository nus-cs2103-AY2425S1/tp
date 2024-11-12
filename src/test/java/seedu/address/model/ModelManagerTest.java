package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_ID;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;
import static seedu.address.testutil.TypicalAssignments.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL1;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL2;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL3;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;
import seedu.address.model.tut.exceptions.TutNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.StudentBuilder;

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
    public void hasTutorial_tutorialInModel_returnsTrue() {
        Tutorial tutorial = Tutorial.of(new TutName("Tut"), TutorialId.of("T1000"));
        modelManager.addTutorial(tutorial);
        assertTrue(modelManager.hasTutorial(tutorial));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(0));
    }

    @Test
    public void hasTutorial_byTutorialId_returnsTrue() {
        Tutorial tutorial = Tutorial.of(new TutName("Tut"), TutorialId.of("T1000"));
        modelManager.addTutorial(tutorial);
        assertTrue(modelManager.hasTutorial(TutorialId.of("T1000")));
    }

    @Test
    public void assignTutorial_success() {
        modelManager.addTutorial(TUTORIAL2);
        modelManager.assignStudent(ALICE, TUTORIAL_ID);
        assertTrue(modelManager.hasTutorial(TUTORIAL_ID));
    }

    @Test
    public void assignTutorialTest_fail() {
        assertThrows(TutNotFoundException.class, () -> modelManager.assignStudent(ALICE, TUTORIAL_ID));
    }

    @Test
    public void deleteTutorialTest_fail() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteTutorial(null));
    }

    @Test
    public void deleteTutorialTest_success() {
        AddressBook addressBook = new AddressBook();
        TutorialList tutorialList = new TutorialList();
        modelManager = new ModelManager(addressBook, new UserPrefs(), new AssignmentList(), tutorialList);

        Tutorial tutorial = Tutorial.of(new TutName("CS2103"), TutorialId.of("T1000"));
        tutorial.add(ALICE);
        tutorial.add(BENSON);

        modelManager.addTutorial(tutorial);

        modelManager.addStudent(ALICE);
        modelManager.addStudent(BENSON);

        assertTrue(modelManager.hasTutorial(tutorial));

        modelManager.deleteTutorial(tutorial);

        assertFalse(modelManager.hasTutorial(tutorial));

        TutorialId noneTutorialId = TutorialId.none();
        Student updatedStudent1 = modelManager.getAddressBook().getStudentList().stream()
                .filter(s -> s.isSameStudent(ALICE))
                .findFirst().orElse(null);
        Student updatedStudent2 = modelManager.getAddressBook().getStudentList().stream()
                .filter(s -> s.isSameStudent(BENSON))
                .findFirst().orElse(null);

        assertEquals(updatedStudent1.getTutorialId(), noneTutorialId);
        assertEquals(updatedStudent2.getTutorialId(), noneTutorialId);
    }

    @Test
    public void setStudentAttendance_validInputs_returnsTrue() {
        ModelManager modelManager = new ModelManager(new AddressBook(), new UserPrefs(),
                new AssignmentList(), new TutorialList());

        Date date = getTodayDateWithoutTime();

        modelManager.addStudent(BOB);
        modelManager.addTutorial(TUTORIAL1);

        modelManager.assignStudent(BOB, TUTORIAL1.getTutorialId());

        boolean result = modelManager.setStudentAttendance(BOB.getStudentId(), TUTORIAL1.getTutorialId(), date);
        assertTrue(result);
    }

    @Test
    public void setStudentAttendance_studentNotInTutorial_returnFalse() {
        ModelManager modelManager = new ModelManager(new AddressBook(), new UserPrefs(),
                new AssignmentList(), new TutorialList());

        Date date = getTodayDateWithoutTime();

        modelManager.addStudent(ALICE);
        modelManager.addStudent(BOB);
        modelManager.addTutorial(TUTORIAL3);

        modelManager.assignStudent(ALICE, TUTORIAL3.getTutorialId());

        boolean result = modelManager.setStudentAttendance(BOB.getStudentId(), TUTORIAL3.getTutorialId(), date);
        assertFalse(result);
    }

    @Test
    public void setStudentAbsent_validInputs_returnsTrue() {
        ModelManager modelManager = new ModelManager(new AddressBook(), new UserPrefs(),
                new AssignmentList(), new TutorialList());

        Date date = getTodayDateWithoutTime();

        modelManager.addStudent(ALICE);
        modelManager.addTutorial(TUTORIAL1);

        modelManager.assignStudent(ALICE, TUTORIAL1.getTutorialId());

        modelManager.setStudentAttendance(ALICE.getStudentId(),
                TUTORIAL1.getTutorialId(), date);

        boolean result = modelManager.setStudentAbsent(ALICE.getStudentId(),
                TUTORIAL1.getTutorialId(), date);

        assertTrue(result);
    }

    @Test
    public void setStudentAbsent_studentNotInTutorial_returnFalse() {
        ModelManager modelManager = new ModelManager(new AddressBook(), new UserPrefs(),
                new AssignmentList(), new TutorialList());

        Date date = getTodayDateWithoutTime();

        modelManager.addStudent(ALICE);
        modelManager.addStudent(BOB);
        modelManager.addTutorial(TUTORIAL1);

        modelManager.assignStudent(ALICE, TUTORIAL1.getTutorialId());

        modelManager.setStudentAttendance(ALICE.getStudentId(),
                TUTORIAL1.getTutorialId(), date);

        boolean result = modelManager.setStudentAbsent(BOB.getStudentId(), TUTORIAL1.getTutorialId(), date);
        assertFalse(result);
    }

    private static Date getTodayDateWithoutTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withStudent(ALICE).withStudent(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        AssignmentList assignmentList = new AssignmentList();
        TutorialList tutorialList = new TutorialList();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, assignmentList, tutorialList);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, assignmentList, tutorialList);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different addressBook -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAddressBook, userPrefs,
                assignmentList, tutorialList));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(addressBook, userPrefs, assignmentList, tutorialList));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(addressBook, differentUserPrefs,
                assignmentList, tutorialList));
    }

    @Test
    public void unassignStudent_fromExistingTutorial_studentRemoved() {
        // Initialize the ModelManager with an address book and tutorials
        AddressBook addressBook = new AddressBook();
        TutorialList tutorialList = new TutorialList();
        ModelManager modelManager = new ModelManager(addressBook, new UserPrefs(), new AssignmentList(), tutorialList);

        // Create a tutorial and add it to the tutorial list
        TutorialId tutorialId = TutorialId.of("T1001");
        TutName tutName = new TutName("CS2103");
        Tutorial tutorial = Tutorial.of(tutName, tutorialId);
        modelManager.addTutorial(tutorial);

        // Create a student and assign them to the tutorial
        Student student = new StudentBuilder().withName("Alice")
                .withStudentId("A0123456X").withTutorialId("T1001").build();
        modelManager.addStudent(student);
        modelManager.assignStudent(student, tutorialId);

        // Verify the student is in the tutorial
        assertTrue(tutorial.getStudents().contains(student));

        // Unassign the student from the tutorial
        modelManager.unassignStudent(student, tutorialId);

        // Verify the student is no longer in the tutorial
        assertFalse(tutorial.getStudents().contains(student));
    }

    @Test
    public void unassignStudent_withUnassignedTutorialId_noActionTaken() {
        // Initialize the ModelManager
        ModelManager modelManager = new ModelManager(new AddressBook(),
                new UserPrefs(), new AssignmentList(), new TutorialList());

        // Create a student with an unassigned tutorial ID
        Student student = new StudentBuilder().withName("Bob").withStudentId("A1234567X")
                .withTutorialId(TutorialId.none().toString()).build();
        modelManager.addStudent(student);

        // Unassign the student (should have no effect)
        modelManager.unassignStudent(student, student.getTutorialId());

        // Since the tutorial ID is unassigned, nothing should happen
        // Verify that no exceptions are thrown and the student's tutorial ID remains the same
        assertEquals(TutorialId.none().toString(), student.getTutorialId().getValue());
    }

    @Test
    public void unassignStudent_fromNonExistentTutorial_noExceptionThrown() {
        // Initialize the ModelManager
        ModelManager modelManager = new ModelManager(new AddressBook(),
                new UserPrefs(), new AssignmentList(), new TutorialList());

        // Create a student assigned to a tutorial ID that doesn't exist in the tutorial list
        TutorialId nonExistentTutorialId = TutorialId.of("T9999");
        Student student = new StudentBuilder().withName("Charlie").withStudentId("A7654321X")
                .withTutorialId(nonExistentTutorialId.getValue()).build();
        modelManager.addStudent(student);

        // Attempt to unassign the student from the non-existent tutorial
        modelManager.unassignStudent(student, nonExistentTutorialId);

        // Verify that no exceptions are thrown and the student's tutorial ID remains unchanged
        assertEquals(nonExistentTutorialId, student.getTutorialId());
    }

    @Test
    public void deleteAssignmentTest() {
        ModelManager modelManager = new ModelManager(new AddressBook(),
                new UserPrefs(), getTypicalAssignmentList(), new TutorialList());

        String res = modelManager.deleteAssignment(new Assignment("Assignment 1",
                LocalDateTime.of(2025, 11, 10, 23, 59)));

        assertEquals(res, ASSIGNMENT1.toStringWithoutStats());
    }
}

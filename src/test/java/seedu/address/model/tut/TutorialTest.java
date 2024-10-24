package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.NONE;
import static seedu.address.testutil.TutUtil.TUTORIAL_ID;
import static seedu.address.testutil.TutUtil.TUTORIAL_SAMPLE;
import static seedu.address.testutil.TutUtil.TUT_DATE;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.AMY;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tut.exceptions.NoTutorialException;
import seedu.address.testutil.StudentBuilder;

public class TutorialTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tutorial.of(null, TUTORIAL_ID));
        assertThrows(NullPointerException.class, () -> Tutorial.of(new TutName(TUT_NAME), (TutorialId) null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Tutorial.of(new TutName(""), TUTORIAL_ID));
    }
    @Test
    public void constructor_nullTutorialId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tutorial.of(new TutName(TUT_NAME), (TutorialId) null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTutName = "";
        assertThrows(IllegalArgumentException.class, () -> Tutorial.of(new TutName(""), TUTORIAL_ID));
    }

    @Test
    public void successAddStudentToTutorial() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        tutorial.add(aliceCopy);
        assertEquals(aliceCopy, tutorial.getStudents().get(0));
    }

    @Test
    public void checkEquals_sameInstance() {
        Tutorial sameTutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        assertEquals(sameTutorial, sameTutorial);
    }

    @Test
    public void checkEquals_differentInstance() {
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        Tutorial otherTutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        assertEquals(tutorial, otherTutorial);
    }

    @Test
    public void checkEquals_differentObjects() {
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        Object notATut = new Object();
        assertNotEquals(tutorial, notATut);
    }

    @Test
    public void checkToString() {
        assertEquals(TUTORIAL_SAMPLE.toString(), TUT_NAME + ": Tutorial " + TUTORIAL_ID);
    }

    @Test
    public void addStudent_duplicateStudent_noAdd() {
        // Adding the same student twice should not add a duplicate
        Student aliceCopy = new StudentBuilder(ALICE).build();
        TUTORIAL_SAMPLE.add(aliceCopy);
        int initialSize = TUTORIAL_SAMPLE.getStudents().size();

        // Try adding again
        TUTORIAL_SAMPLE.add(aliceCopy);
        assertEquals(TUTORIAL_SAMPLE.getStudents().size(), initialSize);
    }

    @Test
    public void addTutorialDate_duplicateDate_noAdd() {
        TUTORIAL_SAMPLE.addTutorialDate(TUT_DATE);
        int initialSize = TUTORIAL_SAMPLE.getTutDates().size();

        TUTORIAL_SAMPLE.addTutorialDate(TUT_DATE);
        assertEquals(TUTORIAL_SAMPLE.getTutDates().size(), initialSize);
    }

    @Test
    public void tutorialDateInList_returnsCorrectly() {
        TUTORIAL_SAMPLE.addTutorialDate(TUT_DATE);
        assertTrue(TUTORIAL_SAMPLE.tutorialDateInList(TUT_DATE));
    }

    @Test
    public void getTutorialDate_returnsCorrectDate() {
        TUTORIAL_SAMPLE.addTutorialDate(TUT_DATE);
        assertEquals(TUTORIAL_SAMPLE.getTutorialDate(TUT_DATE.getDate()), TUT_DATE);
    }

    @Test
    public void getTutorialDates() {
        TUTORIAL_SAMPLE.addTutorialDate(TUT_DATE);
        assertTrue(TUTORIAL_SAMPLE.getTutDates().contains(TUT_DATE));
    }

    @Test
    public void getTutorialId() {
        assertTrue(TUTORIAL_SAMPLE.getTutorialId().equals(TUTORIAL_ID));
    }

    @Test
    public void setAttendance_success() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        tutorial.add(aliceCopy);
        Date date = new Date();
        StudentId aliceId = aliceCopy.getStudentId();
        boolean result = tutorial.setAttendance(date, aliceId);
        assertTrue(result);
    }

    @Test
    public void setAttendance_studentNotInTut_false() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        Date date = new Date();
        StudentId aliceId = aliceCopy.getStudentId();
        boolean result = tutorial.setAttendance(date, aliceId);
        assertFalse(result);
    }

    @Test
    public void setAttendance_nullDate_throwsNullPointerException() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        tutorial.add(aliceCopy);
        StudentId aliceId = aliceCopy.getStudentId();
        assertThrows(NullPointerException.class, () -> tutorial.setAttendance(null, aliceId));
    }

    @Test
    public void setAttendance_nullStudentId_throwsNullPointerException() {
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        Date date = new Date();
        assertThrows(NullPointerException.class, () -> tutorial.setAttendance(date, null));
    }

    @Test
    public void studentInList_studentNotInListTest() {
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        tutorial.add(ALICE);
        tutorial.deleteStudent(ALICE);
        assertFalse(tutorial.studentInList(ALICE));
    }

    @Test
    public void studentInListTest() {
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        tutorial.add(ALICE);
        assertTrue(tutorial.studentInList(ALICE));
    }

    @Test
    public void deleteStudentTest() {
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        tutorial.add(ALICE);
        assertTrue(tutorial.studentInList(ALICE));
        tutorial.deleteStudent(ALICE);
        assertFalse(tutorial.studentInList(ALICE));
    }

    @Test
    public void deleteStudentFailTest() {
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        assertThrows(StudentNotFoundException.class, ()
                -> tutorial.deleteStudent(ALICE));
    }

    @Test
    public void constructor_none_singleton() {
        Tutorial none = Tutorial.none();
        Tutorial none1 = Tutorial.none();
        assertEquals(none, none1);
    }

    @Test
    public void addStudent_noneTest() {
        NONE.add(ALICE);
        assertTrue(NONE.getStudents().contains(ALICE));
    }

    @Test
    public void addStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> NONE.add(null));
    }

    @Test
    public void setAttendance_noneTest() {
        assertThrows(NoTutorialException.class, ()
                -> NONE.setAttendance(new Date(), new StudentId("1000")));
    }

    @Test
    public void getTutName_noneTest() {
        assertEquals(NONE.getTutName(), new TutName("empty"));
    }

    @Test
    public void tutorialDateInList_noneTest() {
        assertThrows(NoTutorialException.class, ()
                -> NONE.tutorialDateInList(TUT_DATE));
    }

    @Test
    public void addTutorialDate_noneTest() {
        assertThrows(NoTutorialException.class, ()
                -> NONE.addTutorialDate(TUT_DATE));
    }

    @Test
    public void getTutorialDate_noneTest() {
        assertEquals(NONE.getTutorialDate(new Date()), new TutDate(new Date()));
    }

    @Test
    public void isValidTutorialDate_noneTest() {
        assertThrows(NoTutorialException.class, ()
                -> NONE.isValidTutorialDate(TUT_DATE));
    }

    @Test
    public void getTutDates_noneTest() {
        assertEquals(NONE.getTutDates(), new ArrayList<>());
    }

    @Test
    public void getTutorialId_noneTest() {
        assertEquals(NONE.getTutorialId(), TutorialId.none());
    }

    @Test
    public void studentInList_noneTest() {
        NONE.add(ALICE);
        assertTrue(NONE.studentInList(ALICE));
    }

    @Test
    public void deleteStudent_noneTest() {
        NONE.add(ALICE);
        assertTrue(NONE.studentInList(ALICE));
        NONE.deleteStudent(ALICE);
        assertFalse(NONE.studentInList(ALICE));
    }

    @Test
    public void deleteStudent_fail_noneTest() {
        assertThrows(StudentNotFoundException.class, ()
                -> NONE.deleteStudent(AMY));
    }
}

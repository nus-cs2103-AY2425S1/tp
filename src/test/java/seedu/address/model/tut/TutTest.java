package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_CLASS;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TypicalStudents.ALICE;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.testutil.StudentBuilder;

public class TutTest {

    private static final String VALID_TUT_CLASS_STRING = "1001";

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(null, TUTORIAL_CLASS));
        assertThrows(NullPointerException.class, () -> new Tut(TUT_NAME, (String) null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidTutName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tut(invalidTutName, TUTORIAL_CLASS));
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(null, TUTORIAL_CLASS));
    }

    @Test
    public void constructor_nullTutorialClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(TUT_NAME, (String) null));
    }

    @Test
    public void constructor_invalidTutName_throwsIllegalArgumentException() {
        String invalidTutName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tut(invalidTutName, VALID_TUT_CLASS_STRING));
    }

    @Test
    public void constructor_validStringTutorialClass_success() {
        Tut tut = new Tut(TUT_NAME, VALID_TUT_CLASS_STRING);
        assertTrue(tut.getTutName().equals(TUT_NAME));
        assertTrue(tut.getTutorialClass().toString().equals(VALID_TUT_CLASS_STRING));
    }

    @Test
    public void isValidTutName() {
        // null tut name
        assertThrows(NullPointerException.class, () -> Tut.isValidName(null));
    }

    @Test
    public void successValidTutName() {
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        assertTrue(tut.getTutName().equals(TUT_NAME));
    }
    @Test
    public void successAddStudentToTutorial() {
        // Add a student to tutorial
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        tut.add(aliceCopy);
        assertTrue(aliceCopy.equals(tut.get(ALICE.getName())));
    }

    @Test
    public void successCheckEquals() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        tut.add(aliceCopy);
        Tut otherTut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        otherTut.add(aliceCopy);
        assertTrue(tut.equals(otherTut));
    }

    @Test
    public void checkEquals_differentObjects() {
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        Object notATut = new Object();
        assertFalse(tut.equals(notATut));
    }

    @Test
    public void checkToString() {
        assertTrue(TUT_SAMPLE.toString().equals(TUT_NAME + ": Tutorial " + TUTORIAL_CLASS));
    }

    @Test
    public void addStudent_duplicateStudent_noAdd() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(aliceCopy);
        int initialSize = TUT_SAMPLE.getStudents().size();
        TUT_SAMPLE.add(aliceCopy);
        assertTrue(TUT_SAMPLE.getStudents().size() == initialSize);
    }

    @Test
    public void markAttendance_newTutorialDate_success() {
        Student alice = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(alice);
        TUT_SAMPLE.markAttendance(alice, TUT_DATE);
        assertTrue(TUT_SAMPLE.tutorialDateInList(TUT_DATE));
        assertTrue(TUT_DATE.getStudentIDs().contains(alice.getStudentId()));
    }

    @Test
    public void markAttendance_existingTutorialDate_success() {
        Student alice = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(alice);
        TUT_SAMPLE.addTutorialDate(TUT_DATE);
        TUT_SAMPLE.markAttendance(alice, TUT_DATE);
        assertTrue(TUT_SAMPLE.tutorialDateInList(TUT_DATE));
        assertTrue(TUT_DATE.getStudentIDs().contains(alice.getStudentId()));
    }

    @Test
    public void markAttendance_studentNotInTutorial_addsStudent() {
        Student newStudent = new StudentBuilder().withName("Bob").build();
        TUT_SAMPLE.markAttendance(newStudent, TUT_DATE);
        assertTrue(TUT_SAMPLE.getStudents().contains(newStudent));
        assertTrue(TUT_DATE.getStudentIDs().contains(newStudent.getStudentId()));
    }

    @Test
    public void addTutorialDate_duplicateDate_noAdd() {
        TUT_SAMPLE.addTutorialDate(TUT_DATE);
        int initialSize = TUT_SAMPLE.getTutDates().size();
        TUT_SAMPLE.addTutorialDate(TUT_DATE);
        assertTrue(TUT_SAMPLE.getTutDates().size() == initialSize);
    }

    @Test
    public void tutorialDateInList_returnsCorrectly() {
        TUT_SAMPLE.addTutorialDate(TUT_DATE);
        assertTrue(TUT_SAMPLE.tutorialDateInList(TUT_DATE));
    }

    @Test
    public void getTutorialDate_returnsCorrectDate() {
        TUT_SAMPLE.addTutorialDate(TUT_DATE);
        assertTrue(TUT_SAMPLE.getTutorialDate(TUT_DATE.getDate()).equals(TUT_DATE));
    }

    @Test
    public void getTutorialDates() {
        TUT_SAMPLE.addTutorialDate(TUT_DATE);
        assertTrue(TUT_SAMPLE.getTutDates().contains(TUT_DATE));
    }

    @Test
    public void getTutorialClass() {
        assertTrue(TUT_SAMPLE.getTutorialClass().equals(TUTORIAL_CLASS));
    public void successCheckToString() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        tut.add(aliceCopy);
        assertTrue(tut.toString().equals(TUT_NAME + ": Tutorial " + TUTORIAL_CLASS));
    }

    @Test
    public void setAttendance_success() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        tut.add(aliceCopy);
        Date date = new Date();
        StudentId aliceId = aliceCopy.getStudentId();
        boolean result = tut.setAttendance(date, aliceId);
        assertTrue(result);
    }

    @Test
    public void setAttendance_studentNotInTut_false() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        Date date = new Date();
        StudentId aliceId = aliceCopy.getStudentId();
        boolean result = tut.setAttendance(date, aliceId);
        assertFalse(result);
    }

    @Test
    public void setAttendance_nullDate_throwsNullPointerException() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        tut.add(aliceCopy);
        StudentId aliceId = aliceCopy.getStudentId();
        assertThrows(NullPointerException.class, () -> tut.setAttendance(null, aliceId));
    }

    @Test
    public void setAttendance_nullStudentId_throwsNullPointerException() {
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        Date date = new Date();
        assertThrows(NullPointerException.class, () -> tut.setAttendance(date, null));
    }
}

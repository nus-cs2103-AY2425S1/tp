package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_CLASS;
import static seedu.address.testutil.TutUtil.TUT_DATE;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TutUtil.TUT_SAMPLE;
import static seedu.address.testutil.TypicalStudents.ALICE;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialClass;
import seedu.address.testutil.StudentBuilder;

public class TutTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(null, TUTORIAL_CLASS));
        assertThrows(NullPointerException.class, () -> new Tut(new TutName(TUT_NAME), (TutorialClass) null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tut(new TutName(""), TUTORIAL_CLASS));
    }
    @Test
    public void constructor_nullTutorialClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(new TutName(TUT_NAME), (TutorialClass) null));
    }

    @Test
    public void isValidTutName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TutName.isValidTutName(null));
    }

    @Test
    public void successValidTutName() {
        assertTrue(TutName.isValidTutName(TUT_NAME));
    }

    @Test
    public void successAddStudentToTutorial() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        tut.add(aliceCopy);
        assertTrue(aliceCopy.equals(tut.get(ALICE.getName())));
    }

    @Test
    public void checkEquals_sameInstance() {
        Tut sameTut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        assertTrue(sameTut.equals(sameTut));
    }

    @Test
    public void checkEquals_differentInstance() {
        Tut tut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        Tut otherTut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        assertTrue(tut.equals(otherTut));
    }

    @Test
    public void checkEquals_differentObjects() {
        Tut tut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        Object notATut = new Object();
        assertFalse(tut.equals(notATut));
    }

    @Test
    public void checkToString() {
        assertTrue(TUT_SAMPLE.toString().equals(TUT_NAME + ": Tutorial " + TUTORIAL_CLASS));
    }

    @Test
    public void addStudent_duplicateStudent_noAdd() {
        // Adding the same student twice should not add a duplicate
        Student aliceCopy = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(aliceCopy);
        int initialSize = TUT_SAMPLE.getStudents().size();

        // Try adding again
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
    }

    @Test
    public void setAttendance_success() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        tut.add(aliceCopy);
        Date date = new Date();
        StudentId aliceId = aliceCopy.getStudentId();
        boolean result = tut.setAttendance(date, aliceId);
        assertTrue(result);
    }

    @Test
    public void setAttendance_studentNotInTut_false() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        Date date = new Date();
        StudentId aliceId = aliceCopy.getStudentId();
        boolean result = tut.setAttendance(date, aliceId);
        assertFalse(result);
    }

    @Test
    public void setAttendance_nullDate_throwsNullPointerException() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        tut.add(aliceCopy);
        StudentId aliceId = aliceCopy.getStudentId();
        assertThrows(NullPointerException.class, () -> tut.setAttendance(null, aliceId));
    }

    @Test
    public void setAttendance_nullStudentId_throwsNullPointerException() {
        Tut tut = new Tut(new TutName(TUT_NAME), TUTORIAL_CLASS);
        Date date = new Date();
        assertThrows(NullPointerException.class, () -> tut.setAttendance(date, null));
    }
}

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
    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(null, TUTORIAL_CLASS));
    }

    @Test
    public void constructor_nullTutorialClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(TUT_NAME, null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTutName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tut(invalidTutName, TUTORIAL_CLASS));
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

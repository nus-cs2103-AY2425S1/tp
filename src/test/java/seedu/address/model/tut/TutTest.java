package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_CLASS;
import static seedu.address.testutil.TutUtil.TUT_DATE;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TutUtil.TUT_SAMPLE;
import static seedu.address.testutil.TypicalStudents.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.model.tut.exceptions.TutDateNotFoundException;
import seedu.address.testutil.StudentBuilder;

public class TutTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(null, TUTORIAL_CLASS));
        assertThrows(NullPointerException.class, () -> new Tut(TUT_NAME, null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidTutName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tut(invalidTutName, TUTORIAL_CLASS));
    }

    @Test
    public void isValidTutName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tut.isValidName(null));
    }

    @Test
    public void successValidTutName() {
        assertTrue(Tut.isValidName(TUT_NAME));
    }

    @Test
    public void successAddStudentToTutorial() {
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        tut.add(aliceCopy);
        assertTrue(aliceCopy.equals(tut.get(ALICE.getName())));
    }

    @Test
    public void checkEquals_sameInstance() {
        Tut sameTut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        assertTrue(sameTut.equals(sameTut));
    }

    @Test
    public void checkEquals_differentInstance() {
        Tut tut = new Tut(TUT_NAME, TUTORIAL_CLASS);
        Tut otherTut = new Tut(TUT_NAME, TUTORIAL_CLASS);
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
        assertTrue(TUT_DATE.getStudents().contains(alice));
    }

    @Test
    public void markAttendance_existingTutorialDate_success() {
        Student alice = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(alice);
        TUT_SAMPLE.addTutorialDate(TUT_DATE);

        TUT_SAMPLE.markAttendance(alice, TUT_DATE);
        assertTrue(TUT_SAMPLE.tutorialDateInList(TUT_DATE));
        assertTrue(TUT_DATE.getStudents().contains(alice));
    }

    @Test
    public void markAttendance_invalidTutorialDate_throwsException() {
        Student alice = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(alice);

        TutDate invalidDate = new TutDate(null);
        assertThrows(TutDateNotFoundException.class, () -> TUT_SAMPLE.markAttendance(alice, invalidDate));
    }

    @Test
    public void markAttendance_studentNotInTutorial_addsStudent() {
        Student newStudent = new StudentBuilder().withName("Bob").build();

        TUT_SAMPLE.markAttendance(newStudent, TUT_DATE);
        assertTrue(TUT_SAMPLE.getStudents().contains(newStudent));
        assertTrue(TUT_DATE.getStudents().contains(newStudent));
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
        assertTrue(TUT_SAMPLE.getTutorialDate(0).equals(TUT_DATE));
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
}

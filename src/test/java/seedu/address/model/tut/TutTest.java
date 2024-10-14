package seedu.address.model.tut;

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
    public void markAttendance_newTutorialDate_success() {
        // Set up TUT_SAMPLE with a student
        Student alice = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(alice);

        // Ensure marking attendance adds the tutorial date if it doesn't already exist
        TUT_SAMPLE.markAttendance(alice, TUT_DATE);
        assertTrue(TUT_SAMPLE.tutorialDateInList(TUT_DATE)); // Check if the date was added
        assertTrue(TUT_DATE.getStudents().contains(alice)); // Check if attendance was marked
    }

    @Test
    public void markAttendance_existingTutorialDate_success() {
        // Set up TUT_SAMPLE with a student
        Student alice = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(alice);

        // Add a tutorial date first
        TUT_SAMPLE.addTutorialDate(TUT_DATE);

        // Mark attendance for an existing date
        TUT_SAMPLE.markAttendance(alice, TUT_DATE);

        // Ensure the date already exists and attendance is marked
        assertTrue(TUT_SAMPLE.tutorialDateInList(TUT_DATE));
        assertTrue(TUT_DATE.getStudents().contains(alice));
    }

    @Test
    public void markAttendance_invalidTutorialDate_throwsException() {
        // Set up TUT_SAMPLE with a student
        Student alice = new StudentBuilder(ALICE).build();
        TUT_SAMPLE.add(alice);

        // Create an invalid tutorial date (null in this case)
        TutDate invalidDate = new TutDate(null);

        // Mark attendance with an invalid tutorial date, expecting an exception
        assertThrows(TutDateNotFoundException.class, () -> TUT_SAMPLE.markAttendance(alice, invalidDate));
    }

    @Test
    public void markAttendance_studentNotInTutorial_shouldAddStudent() {
        // Set up TUT_SAMPLE without adding the student first
        Student newStudent = new StudentBuilder().withName("Bob").build();

        // Mark attendance for this new student (should add them automatically)
        TUT_SAMPLE.markAttendance(newStudent, TUT_DATE);

        // Check if the student was added to the tutorial and their attendance was marked
        assertTrue(TUT_SAMPLE.getStudents().contains(newStudent));
        assertTrue(TUT_DATE.getStudents().contains(newStudent));
    }
}

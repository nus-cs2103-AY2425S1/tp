package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TypicalStudents.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;
public class TutTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tut(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTutName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tut(invalidTutName));
    }

    @Test
    public void isValidTutName() {
        // null tut name
        assertThrows(NullPointerException.class, () -> Tut.isValidName(null));
    }

    @Test
    public void successValidTutName() {
        Tut tut = new Tut(TUT_NAME);
        assertTrue(tut.getTutName().equals(TUT_NAME));
    }
    @Test
    public void successAddStudentToTutorial() {
        // Add a student to tutorial
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Tut tut = new Tut(TUT_NAME);
        tut.add(aliceCopy);
        assertTrue(aliceCopy.equals(tut.get(ALICE.getName())));
    }
}

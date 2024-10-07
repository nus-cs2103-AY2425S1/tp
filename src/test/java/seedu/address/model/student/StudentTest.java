package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.DIDDY;
import static seedu.address.testutil.TypicalStudents.HUGH;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;
public class StudentTest {

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(HUGH.isSameStudent(HUGH));

        // null -> returns false
        assertFalse(HUGH.isSameStudent(null));

        // same student number, all other attributes different -> returns true
        Student editedHugh = new StudentBuilder(HUGH).withContactNumber(VALID_CONTACT_NUMBER_DIDDY)
                .withTutorialGroup(VALID_TUTORIAL_GROUP_DIDDY).withName(VALID_NAME_DIDDY).build();
        assertTrue(HUGH.isSameStudent(editedHugh));

        // different student number, all other attributes same -> returns false
        editedHugh = new StudentBuilder(HUGH).withStudentNumber("A7654321A").build();
        assertFalse(HUGH.isSameStudent(editedHugh));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student hughCopy = new StudentBuilder(HUGH).build();
        assertTrue(HUGH.equals(hughCopy));

        // same object -> returns true
        assertTrue(HUGH.equals(HUGH));

        // null -> returns false
        assertFalse(HUGH.equals(null));

        // different type -> returns false
        assertFalse(HUGH.equals(5));

        // different student -> returns false
        assertFalse(HUGH.equals(DIDDY));

        // different name -> returns false
        Student editedHugh = new StudentBuilder(HUGH).withName(VALID_NAME_DIDDY).build();
        assertFalse(HUGH.equals(editedHugh));

        // different contact number -> returns false
        editedHugh = new StudentBuilder(HUGH).withContactNumber(VALID_CONTACT_NUMBER_DIDDY).build();
        assertFalse(HUGH.equals(editedHugh));

        // different tutorial group -> returns false
        editedHugh = new StudentBuilder(HUGH).withTutorialGroup(VALID_TUTORIAL_GROUP_DIDDY).build();
        assertFalse(HUGH.equals(editedHugh));

        // different student number -> returns false
        editedHugh = new StudentBuilder(HUGH).withStudentNumber(VALID_STUDENT_NUMBER_DIDDY).build();
        assertFalse(HUGH.equals(editedHugh));
    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName() + "{name=" + HUGH.getName() + ", contactNumber=" + HUGH.getContactNumber()
                + ", tutorialGroup=" + HUGH.getTutorialGroup() + ", studentNumber=" + HUGH.getStudentNumber() + "}";
        assertEquals(expected, HUGH.toString());
    }
}

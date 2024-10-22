package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_NAME_A;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalAssignments.SCIENCE_ASSIGNMENT_GRADED;
import static seedu.address.testutil.TypicalStudents.DIDDY;
import static seedu.address.testutil.TypicalStudents.HUGH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.testutil.StudentBuilder;
public class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new StudentBuilder(HUGH).build();

        // Adding assignments to the student
        student.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        student.addAssignment(SCIENCE_ASSIGNMENT_GRADED);
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(HUGH.isSameStudent(HUGH));

        // null -> returns false
        assertFalse(HUGH.isSameStudent(null));

        // same student number, all other attributes different -> returns true
        Student editedHugh = new StudentBuilder(HUGH).withPhone(VALID_PHONE_DIDDY)
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
        editedHugh = new StudentBuilder(HUGH).withPhone(VALID_PHONE_DIDDY).build();
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
        String expected = Student.class.getCanonicalName()
                + "{name=" + HUGH.getName()
                + ", contactNumber=" + HUGH.getPhone()
                + ", tutorialGroup=" + HUGH.getTutorialGroup()
                + ", studentNumber=" + HUGH.getStudentNumber() + "}";
        assertEquals(expected, HUGH.toString());
    }

    @Test
    void deleteAssignment_validAssignment_success() throws CommandException {
        AssignmentQuery query = new AssignmentQuery(ASSIGNMENT_NAME_A, null, null, null, null);
        assertEquals(MATH_ASSIGNMENT_SUBMITTED, student.deleteAssignment(query));
    }

    @Test
    void deleteAssignment_nonExistentAssignment_returnsNull() throws CommandException {
        // Create an assignment query that does not match any existing assignment
        AssignmentQuery query = new AssignmentQuery(new AssignmentName("Nonexistent Assignment"),
                null, null, null, null);

        // Execute delete
        Assignment deletedAssignment = student.deleteAssignment(query);

        // Verify that the method returns null when the assignment does not exist
        assertEquals(null, deletedAssignment);
    }

    @Test
    void deleteAssignment_nullQuery_throwsException() {
        // Verify that passing a null query throws an exception
        assertThrows(NullPointerException.class, () -> student.deleteAssignment(null));
    }

}

package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.MATH_HOMEWORK;
import static seedu.address.testutil.TypicalAssignments.PHYSICS_HOMEWORK;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.exceptions.AssignmentIndexOutOfRangeException;
import seedu.address.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // same name, all other attributes different -> returns true
        Student editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Student editedBob = new StudentBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameStudent(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new StudentBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameStudent(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different student -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", tags=" + ALICE.getTags() + ", assignments="
                + ALICE.getAssignmentList() + ", remark=" + ALICE.getRemark() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hasAssignment() {
        Student studentWithAssignment = new StudentBuilder(ALICE)
                .withAssignments(List.of(MATH_HOMEWORK))
                .buildWithAssignment();

        System.out.println(studentWithAssignment);
        // same assignment -> returns true
        assertTrue(studentWithAssignment.hasAssignment(MATH_HOMEWORK));

        // different assignment -> returns false
        assertFalse(studentWithAssignment.hasAssignment(PHYSICS_HOMEWORK));
    }

    @Test
    public void addAssignment() {
        Student student = new StudentBuilder(ALICE).build();

        // add assignment -> assignment should be in assignment list
        Student studentWithAssignment = student.addAssignment(MATH_HOMEWORK);
        assertTrue(studentWithAssignment.hasAssignment(MATH_HOMEWORK));
        // original student remains unchanged
        assertFalse(student.hasAssignment(MATH_HOMEWORK));
    }

    @Test
    public void deleteAssignment_validIndex() {
        List<Assignment> assignments = new ArrayList<Assignment>(Arrays.asList(MATH_HOMEWORK, PHYSICS_HOMEWORK));
        Student student = new StudentBuilder(ALICE).withAssignments(assignments).buildWithAssignment();

        // delete first assignment
        student.deleteAssignment(1);

        // assignment list should contain only the second assignment
        assertEquals(1, student.getAssignmentList().size());
        assertEquals(PHYSICS_HOMEWORK, student.getAssignmentList().get(0));
    }

    @Test
    public void deleteAssignment_invalidIndex_throwsAssignmentIndexOutOfRangeException() {
        Student student = new StudentBuilder(ALICE).withAssignments(
                new ArrayList<Assignment>(Arrays.asList(MATH_HOMEWORK, PHYSICS_HOMEWORK))).buildWithAssignment();

        // invalid index -> throws AssignmentIndexOutOfRangeException
        assertThrows(AssignmentIndexOutOfRangeException.class, () -> student.deleteAssignment(3));
        assertThrows(AssignmentIndexOutOfRangeException.class, () -> student.deleteAssignment(0));
    }
    @Test
    public void setAssignmentList() {
        Student student = new StudentBuilder(ALICE).withAssignments(Arrays.asList(PHYSICS_HOMEWORK)).build();

        // set assignment list to a new list with different assignments
        List<Assignment> newAssignments = Arrays.asList(MATH_HOMEWORK);
        Student updatedStudent = student.setAssignmentList(newAssignments);

        // original student remains unchanged
        assertNotEquals(student.getAssignmentList(), updatedStudent.getAssignmentList());
        assertEquals(newAssignments, updatedStudent.getAssignmentList());
    }
}

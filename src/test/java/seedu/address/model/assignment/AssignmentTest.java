package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAssignments.ALICE_ALPHA;
import static seedu.address.testutil.TypicalAssignments.BENSON_BETA;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalProjects.BETA;

import org.junit.jupiter.api.Test;

import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.ProjectId;
import seedu.address.testutil.AssignmentBuilder;

public class AssignmentTest {

    @Test
    public void isSameAssignment() {
        // same object -> returns true
        assertTrue(ALICE_ALPHA.isSameAssignment(ALICE_ALPHA));

        // null -> returns false
        assertFalse(ALICE_ALPHA.isSameAssignment((Assignment) null));

        // same assignmentId -> returns true
        assertTrue(ALICE_ALPHA.isSameAssignment(ALICE_ALPHA.getAssignmentId()));

        // null assignmentId-> returns false
        assertFalse(ALICE_ALPHA.isSameAssignment((AssignmentId) null));

        // same projectId and employeeId  -> returns true
        assertTrue(ALICE_ALPHA.isSameAssignment(ALPHA.getId(), ALICE.getEmployeeId()));

        // null projectId and employeeId -> returns false
        assertFalse(ALICE_ALPHA.isSameAssignment((ProjectId) null, (EmployeeId) null));

        // assignmentId differs, all other attributes same -> returns true
        Assignment editedAlice = new AssignmentBuilder(ALICE_ALPHA).withAssignmentId("3").build();
        assertTrue(ALICE_ALPHA.isSameAssignment(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Assignment aliceCopy = new AssignmentBuilder(ALICE_ALPHA).build();
        assertTrue(ALICE_ALPHA.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_ALPHA.equals(ALICE_ALPHA));

        // null -> returns false
        assertFalse(ALICE_ALPHA.equals(null));

        // different type -> returns false
        assertFalse(ALICE_ALPHA.equals(5));

        // different assignment -> returns false
        assertFalse(ALICE_ALPHA.equals(BENSON_BETA));

        // different assignmentId -> returns false
        Assignment editedAlice = new AssignmentBuilder(ALICE_ALPHA).withAssignmentId("100").build();
        assertFalse(ALICE_ALPHA.equals(editedAlice));

        // different project -> returns false
        editedAlice = new AssignmentBuilder(ALICE_ALPHA).withProject(BETA).build();
        assertFalse(ALICE_ALPHA.equals(editedAlice));

        // different person -> returns false
        editedAlice = new AssignmentBuilder(ALICE_ALPHA).withPerson(BENSON).build();
        assertFalse(ALICE_ALPHA.equals(editedAlice));
    }
}

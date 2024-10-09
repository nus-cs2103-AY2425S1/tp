package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test class for the ProjectStatus field in Person class.
 */
public class ProjectStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProjectStatus(null));
    }

    @Test
    public void constructor_invalidProjectStatus_throwsIllegalArgumentException() {
        String invalidStatus = "not a valid status";
        assertThrows(IllegalArgumentException.class, () -> new ProjectStatus(invalidStatus));
    }

    @Test
    public void isValidProjectStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> ProjectStatus.isValidProjectStatus(null));

        // invalid project status
        assertFalse(ProjectStatus.isValidProjectStatus("")); // empty string
        assertFalse(ProjectStatus.isValidProjectStatus(" ")); // spaces only
        assertFalse(ProjectStatus.isValidProjectStatus("ongoing")); // not 'in progress' or 'completed'
        assertFalse(ProjectStatus.isValidProjectStatus("completeded")); // typo

        // valid project status
        assertTrue(ProjectStatus.isValidProjectStatus("in progress")); // correct lower case
        assertTrue(ProjectStatus.isValidProjectStatus("IN PROGRESS")); // case insensitive
        assertTrue(ProjectStatus.isValidProjectStatus("Completed")); // case insensitive
        assertTrue(ProjectStatus.isValidProjectStatus("completed")); // correct lower case
    }

    @Test
    public void toString_validStatus_returnsCorrectString() {
        ProjectStatus inProgressStatus = new ProjectStatus("in progress");
        assertTrue(inProgressStatus.toString().equals("in progress"));

        ProjectStatus completedStatus = new ProjectStatus("completed");
        assertTrue(completedStatus.toString().equals("completed"));
    }

    @Test
    public void equals() {
        ProjectStatus inProgress = new ProjectStatus("in progress");
        ProjectStatus completed = new ProjectStatus("completed");

        // same values -> returns true
        assertTrue(inProgress.equals(new ProjectStatus("in progress")));
        assertTrue(completed.equals(new ProjectStatus("completed")));

        // same object -> returns true
        assertTrue(inProgress.equals(inProgress));

        // null -> returns false
        assertFalse(inProgress.equals(null));

        // different types -> returns false
        assertFalse(inProgress.equals(5.0f));

        // different values -> returns false
        assertFalse(inProgress.equals(completed));
    }

    @Test
    public void hashCode_sameStatus_sameHashCode() {
        ProjectStatus inProgress1 = new ProjectStatus("in progress");
        ProjectStatus inProgress2 = new ProjectStatus("IN PROGRESS");
        assertTrue(inProgress1.hashCode() == inProgress2.hashCode());

        ProjectStatus completed1 = new ProjectStatus("completed");
        ProjectStatus completed2 = new ProjectStatus("COMPLETED");
        assertTrue(completed1.hashCode() == completed2.hashCode());
    }
}

package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

public class TaskTest {

    private static final String VALID_DESCRIPTION_ONE = "Administer medication";
    private static final String VALID_DESCRIPTION_TWO = "Routine check-up";

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        // Test for null patient
        assertThrows(NullPointerException.class, () -> new Task(null, VALID_DESCRIPTION_ONE));

        // Test for null description
        assertThrows(NullPointerException.class, () -> new Task(ALICE, null));

        // Test for both null patient and description
        assertThrows(NullPointerException.class, () -> new Task(null, null));
    }

    @Test
    public void constructor_validFields_setsStatusAsIncompleteByDefault() {
        Task incompleteTask = new Task(ALICE, VALID_DESCRIPTION_ONE, false);
        Task completeTask = new Task(ALICE, VALID_DESCRIPTION_ONE, true);

        assertFalse(incompleteTask.getStatus());
        assertTrue(completeTask.getStatus());
    }

    @Test
    public void getPatient_validTask_returnsCorrectPatient() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertEquals(ALICE, task.getPatient());
    }

    @Test
    public void getDescription_validTask_returnsCorrectDescription() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertEquals(VALID_DESCRIPTION_ONE, task.getDescription());
    }

    @Test
    public void getStatus_incompleteTask_returnsFalse() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertFalse(task.getStatus());
    }

    @Test
    public void getStatus_completeTask_returnsTrue() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE, true);
        assertTrue(task.getStatus());
    }

    @Test
    public void getStatusString_incompleteTask_returnsIncomplete() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertEquals("Incomplete", task.getStatusString());
    }

    @Test
    public void getStatusString_completeTask_returnsComplete() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE, true);
        assertEquals("Complete", task.getStatusString());
    }

    @Test
    public void markTaskComplete_incompleteTask_marksAsComplete() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertFalse(task.getStatus()); // initially incomplete

        task.markTaskComplete();
        assertTrue(task.getStatus()); // now marked as complete
        assertEquals("Complete", task.getStatusString());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Task taskOne = new Task(ALICE, VALID_DESCRIPTION_ONE);
        Task taskOneCopy = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertTrue(taskOne.equals(taskOneCopy));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Task taskOne = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertTrue(taskOne.equals(taskOne));
    }

    @Test
    public void equals_null_returnsFalse() {
        Task taskOne = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertFalse(taskOne.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Task taskOne = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertFalse(taskOne.equals(5));
    }

    @Test
    public void equals_differentDescription_returnsFalse() {
        Task taskOne = new Task(ALICE, VALID_DESCRIPTION_ONE);
        Task taskTwo = new Task(ALICE, VALID_DESCRIPTION_TWO);
        assertFalse(taskOne.equals(taskTwo));
    }

    @Test
    public void equals_differentPatient_returnsFalse() {
        Task taskOne = new Task(ALICE, VALID_DESCRIPTION_ONE);
        Task taskWithDifferentPerson = new Task(BOB, VALID_DESCRIPTION_ONE);
        assertFalse(taskOne.equals(taskWithDifferentPerson));
    }

    @Test
    public void toString_validTask_returnsExpectedString() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        String expected = Task.class.getCanonicalName()
                + "{patient=" + task.getPatient()
                + ", description=" + task.getDescription()
                + ", status=" + task.getStatusString()
                + "}";
        assertEquals(expected, task.toString());
    }
}

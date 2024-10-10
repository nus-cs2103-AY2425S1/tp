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
    public void constructor_null_throwsNullPointerException() {
        // Test for null patient
        assertThrows(NullPointerException.class, () -> new Task(null, VALID_DESCRIPTION_ONE));

        // Test for null description
        assertThrows(NullPointerException.class, () -> new Task(ALICE, null));
    }

    @Test
    public void equals() {
        Task taskOne = new Task(ALICE, VALID_DESCRIPTION_ONE);
        Task taskOneCopy = new Task(ALICE, VALID_DESCRIPTION_ONE);
        Task taskTwo = new Task(ALICE, VALID_DESCRIPTION_TWO);
        Task taskWithDifferentPerson = new Task(BOB, VALID_DESCRIPTION_ONE);

        // same values -> returns true
        assertTrue(taskOne.equals(taskOneCopy));

        // same object -> returns true
        assertTrue(taskOne.equals(taskOne));

        // null -> returns false
        assertFalse(taskOne.equals(null));

        // different type -> returns false
        assertFalse(taskOne.equals(5));

        // different task description -> returns false
        assertFalse(taskOne.equals(taskTwo));

        // different person -> returns false
        assertFalse(taskOne.equals(taskWithDifferentPerson));
    }

    @Test
    public void toStringMethod() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        String expected = Task.class.getCanonicalName()
                + "{patient=" + task.getPatient()
                + ", description=" + task.getDescription()
                + "}";
        assertEquals(expected, task.toString());
    }

    @Test
    public void getPatient() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertEquals(ALICE, task.getPatient());
    }

    @Test
    public void getDescription() {
        Task task = new Task(ALICE, VALID_DESCRIPTION_ONE);
        assertEquals(VALID_DESCRIPTION_ONE, task.getDescription());
    }
}

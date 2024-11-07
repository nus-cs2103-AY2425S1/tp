package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_validStatus_success() {
        // valid status inputs
        Status status1 = new Status("Y");
        Status status2 = new Status("y");
        Status status3 = new Status("N");
        Status status4 = new Status("n");

        assertEquals(Status.State.Y, status1.status);
        assertEquals(Status.State.Y, status2.status);
        assertEquals(Status.State.N, status3.status);
        assertEquals(Status.State.N, status4.status);
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        // invalid status inputs
        assertThrows(IllegalArgumentException.class, () -> new Status("Yes"));
        assertThrows(IllegalArgumentException.class, () -> new Status("No"));
        assertThrows(IllegalArgumentException.class, () -> new Status("1"));
        assertThrows(IllegalArgumentException.class, () -> new Status(" "));
    }

    @Test
    public void isValidStatus() {
        // valid statuses
        assertTrue(Status.isValidStatus("Y"));
        assertTrue(Status.isValidStatus("y"));
        assertTrue(Status.isValidStatus("N"));
        assertTrue(Status.isValidStatus("n"));

        // invalid statuses
        assertFalse(Status.isValidStatus("Yes")); // incorrect format
        assertFalse(Status.isValidStatus("no")); // lowercase word
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // space only
        assertFalse(Status.isValidStatus("123")); // numeric string
    }

    @Test
    public void testGetDefault() {
        // default status should be N
        Status defaultStatus = Status.getDefault();
        assertEquals(Status.State.N, defaultStatus.status);
    }

    @Test
    public void testIsGraded() {
        Status graded = new Status("Y");
        Status notGraded = new Status("N");

        assertTrue(graded.isSubmitted()); // should be true for Y
        assertFalse(notGraded.isSubmitted()); // should be false for N
    }

    @Test
    public void testToString() {
        Status statusY = new Status("Y");
        Status statusN = new Status("N");

        assertEquals("Y", statusY.toString());
        assertEquals("N", statusN.toString());
    }

    @Test
    public void testEquals() {
        Status status1 = new Status("Y");
        Status status2 = new Status("Y");
        Status status3 = new Status("N");

        assertTrue(status1.equals(status2)); // same status
        assertFalse(status1.equals(status3)); // different status
        assertFalse(status1.equals(null)); // null comparison
        assertFalse(status1.equals("Y")); // different type
    }
}

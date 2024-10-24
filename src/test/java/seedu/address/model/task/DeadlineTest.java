package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    private static final String VALID_DESCRIPTION = "Submit assignment";
    private static final String VALID_BY_DATE = "2023-12-31";
    private static final String VALID_BY_DATE_FORMATTED = "Dec 31 2023";
    private static final Description DESCRIPTION_OBJ = new Description(VALID_DESCRIPTION);
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    @Test
    public void constructor_validStringDescriptionAndByDate_success() {
        Deadline deadline = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        assertEquals("[D][ ] Submit assignment (by: Dec 31 2023)", deadline.toString());
    }

    @Test
    public void constructor_validDescriptionObject_success() {
        Deadline deadline = new Deadline(DESCRIPTION_OBJ.toString(), VALID_BY_DATE);
        assertEquals("[D][ ] Submit assignment (by: Dec 31 2023)", deadline.toString());
    }

    @Test
    public void constructor_withDoneStatus_success() {
        Deadline deadline = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE, true);
        assertEquals("[D][X] Submit assignment (by: Dec 31 2023)", deadline.toString());
    }

    @Test
    public void getBy_returnsCorrectDate() {
        Deadline deadline = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        assertEquals(VALID_BY_DATE, deadline.getBy().toString());
    }

    @Test
    public void markAsDone_marksTaskAsDone() {
        Deadline deadline = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        deadline.markAsDone();
        assertTrue(deadline.getIsDone());
        assertEquals("[D][X] Submit assignment (by: Dec 31 2023)", deadline.toString());
    }

    @Test
    public void markAsUndone_marksTaskAsUndone() {
        Deadline deadline = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE, true);
        deadline.markAsUndone();
        assertFalse(deadline.getIsDone());
        assertEquals("[D][ ] Submit assignment (by: Dec 31 2023)", deadline.toString());
    }

    @Test
    public void equals_sameDeadline_returnsTrue() {
        Deadline deadline1 = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        Deadline deadline2 = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        assertTrue(deadline1.equals(deadline2));
    }

    @Test
    public void equals_differentDeadline_returnsFalse() {
        Deadline deadline1 = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        Deadline deadline2 = new Deadline("Complete project", "2023-11-30");
        assertFalse(deadline1.equals(deadline2));
    }

    @Test
    public void hashCode_sameDeadline_returnsSameHashCode() {
        Deadline deadline1 = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        Deadline deadline2 = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        assertEquals(deadline1.hashCode(), deadline2.hashCode());
    }

    @Test
    public void hashCode_differentDeadline_returnsDifferentHashCode() {
        Deadline deadline1 = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        Deadline deadline2 = new Deadline("Complete project", "2023-11-30");
        assertFalse(deadline1.hashCode() == deadline2.hashCode());
    }

    @Test
    public void toString_correctlyFormatsDeadline() {
        Deadline deadline = new Deadline(VALID_DESCRIPTION, VALID_BY_DATE);
        assertEquals("[D][ ] Submit assignment (by: Dec 31 2023)", deadline.toString());
    }
}

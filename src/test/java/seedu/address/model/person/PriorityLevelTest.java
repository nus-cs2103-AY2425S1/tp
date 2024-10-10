package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PriorityLevelTest {

    @Test
    public void equals() {
        PriorityLevel priorityLevel = new PriorityLevel(1);

        // same object -> returns true
        assertTrue(priorityLevel.equals(priorityLevel));

        // same values -> returns true
        PriorityLevel priorityLevelCopy = new PriorityLevel(priorityLevel.getValue());
        assertTrue(priorityLevel.equals(priorityLevelCopy));

        // different types -> returns false
        assertFalse(priorityLevel.equals(1));

        // null -> returns false
        assertFalse(priorityLevel.equals(null));

        // different priority level -> returns false
        PriorityLevel differentPriorityLevel = new PriorityLevel(2);
        assertFalse(priorityLevel.equals(differentPriorityLevel));
    }
}

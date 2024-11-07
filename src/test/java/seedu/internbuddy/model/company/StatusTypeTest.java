package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StatusTypeTest {

    @Test
    public void hasStatusType() {
        // valid values -> true
        assertTrue(StatusType.hasStatusType("INTERESTED"));
        assertTrue(StatusType.hasStatusType("APPLIED"));
        assertTrue(StatusType.hasStatusType("CLOSED"));

        // invalid values -> false
        assertFalse(StatusType.hasStatusType("PERHAPS")); // unrecognised name
        assertFalse(StatusType.hasStatusType("")); // empty string
        assertFalse(StatusType.hasStatusType("  ")); // white space
        assertFalse(StatusType.hasStatusType("  INTERESTED")); // whitespace + valid name
        assertFalse(StatusType.hasStatusType("interested")); // wrong case + valid name

        // null -> false
        assertFalse(StatusType.hasStatusType(null));
    }
}

package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ReminderDescriptionTest {

    private static final String VALID_DESCRIPTION = "This is a valid reminder description.";
    private static final String EMPTY_DESCRIPTION = "";
    private static final String OVER_LENGTH_DESCRIPTION = "A".repeat(ReminderDescription.MAX_LENGTH + 1);
    private static final String UNDER_LENGTH_DESCRIPTION = ""; // Below minimum length of 1
    private static final String VALID_MAX_LENGTH_DESCRIPTION = "A".repeat(ReminderDescription.MAX_LENGTH);
    private static final String VALID_MIN_LENGTH_DESCRIPTION = "A"; // Minimum length of 1

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        // Test empty string
        assertThrows(IllegalArgumentException.class, () -> new ReminderDescription(EMPTY_DESCRIPTION));

        // Test over maximum length
        assertThrows(IllegalArgumentException.class, () -> new ReminderDescription(OVER_LENGTH_DESCRIPTION));
    }

    @Test
    public void constructor_validDescription_success() {
        ReminderDescription description = new ReminderDescription(VALID_DESCRIPTION);
        assertEquals(VALID_DESCRIPTION, description.description);
    }

    @Test
    public void isValidDescription() {
        // Null description
        assertThrows(NullPointerException.class, () -> ReminderDescription.isValidDescription(null));

        // Invalid descriptions
        assertFalse(ReminderDescription.isValidDescription(EMPTY_DESCRIPTION)); // Below minimum length
        assertFalse(ReminderDescription.isValidDescription(OVER_LENGTH_DESCRIPTION)); // Over maximum length
        assertFalse(ReminderDescription.isValidDescription("This\nis invalid")); // Contains newline

        // Valid descriptions
        assertTrue(ReminderDescription.isValidDescription(VALID_DESCRIPTION)); // Normal valid case
        assertTrue(ReminderDescription.isValidDescription(VALID_MIN_LENGTH_DESCRIPTION)); // Minimum length valid case
        assertTrue(ReminderDescription.isValidDescription(VALID_MAX_LENGTH_DESCRIPTION)); // Maximum length valid case
    }

    @Test
    public void equals() {
        ReminderDescription description1 = new ReminderDescription(VALID_DESCRIPTION);
        ReminderDescription description2 = new ReminderDescription(VALID_DESCRIPTION);
        ReminderDescription differentDescription = new ReminderDescription("Different description");

        // Same object -> returns true
        assertTrue(description1.equals(description1));

        // Same value -> returns true
        assertTrue(description1.equals(description2));

        // Different description -> returns false
        assertFalse(description1.equals(differentDescription));

        // Different object type -> returns false
        assertFalse(description1.equals("Not a ReminderDescription object"));
    }

    @Test
    public void hashCode_sameDescription_returnsSameHashCode() {
        ReminderDescription description1 = new ReminderDescription(VALID_DESCRIPTION);
        ReminderDescription description2 = new ReminderDescription(VALID_DESCRIPTION);
        assertEquals(description1.hashCode(), description2.hashCode());
    }

    @Test
    public void toString_formatCorrect() {
        ReminderDescription description = new ReminderDescription(VALID_DESCRIPTION);
        assertEquals("[" + VALID_DESCRIPTION + "]", description.toString());
    }
}

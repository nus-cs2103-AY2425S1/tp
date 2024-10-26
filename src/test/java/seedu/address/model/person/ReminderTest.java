package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

public class ReminderTest {
    private static final String validDate = "01-01-2024";
    private static final String validDescription = "Catch up over lunch";
    private static final Name validName = ALICE.getName();
    private static final String invalidDate = "2024-01-01";
    private static final String invalidDescription = "";
    private static final String INVALID_NAME = "R@chel";
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, null, null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Reminder(invalidDate, validDescription, validName));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Reminder(validDate, invalidDescription, validName));
    }

    @Test
    public void isValidDate() {
        // null as date
        assertThrows(NullPointerException.class, () -> Reminder.isValidDate(null));
        // invalid dates
        assertFalse(Reminder.isValidDate("")); // empty string
        assertFalse(Reminder.isValidDate(" ")); // spaces only
        // valid dates
        assertTrue(Reminder.isValidDate("01-02-2024"));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Reminder.isValidDescription(null));

        // invalid description
        assertFalse(Reminder.isValidDescription("")); // empty string
        assertFalse(Reminder.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Reminder.isValidDescription("Lunch"));
        assertTrue(Reminder.isValidDescription("-")); // one character
        assertTrue(Reminder.isValidDescription("Working together on an extremely important project"));
        // long description
    }

    @Test
    public void equals() {
        Reminder reminder = new Reminder("02-07-2022", "coding together", ALICE.getName());
        // same values -> returns true
        assertTrue(reminder.equals(new Reminder("02-07-2022", "coding together", ALICE.getName())));
        // same object -> returns true
        assertTrue(reminder.equals(reminder));
        // null -> returns false
        assertFalse(reminder.equals(null));
        // different types -> returns false
        assertFalse(reminder.equals(5.0f));
        // different values -> returns false
        assertFalse(reminder.equals(new Reminder("01-01-2022", "flying to China", ALICE.getName())));
    }

}

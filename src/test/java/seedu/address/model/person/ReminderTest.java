package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

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
    public void isSameReminder() {
        Reminder validReminder = new Reminder(validDate, validDescription, validName);

        // same object -> returns true
        assertTrue(validReminder.isSameReminder(validReminder));

        // null -> returns false
        assertFalse(validReminder.isSameReminder(null));

        // same date, all other attributes different -> returns false
        Reminder reminder1 = new Reminder("02-02-2022", "meeting", ALICE.getName());
        Reminder reminder2 = new Reminder("02-02-2022", "dinner", BOB.getName());
        assertFalse(reminder1.isSameReminder(reminder2));

        // same description, all other attributes different -> returns false
        Reminder reminder3 = new Reminder("02-03-2022", "meeting", ALICE.getName());
        Reminder reminder4 = new Reminder("02-02-2022", "meeting", BOB.getName());
        assertFalse(reminder3.isSameReminder(reminder4));

        // same name, all other attributes different -> returns false
        Reminder reminder5 = new Reminder("02-03-2022", "meeting", ALICE.getName());
        Reminder reminder6 = new Reminder("02-02-2022", "dinner", ALICE.getName());
        assertFalse(reminder5.isSameReminder(reminder6));

        // all attributes same -> returns true
        Reminder reminder7 = new Reminder("02-03-2022", "meeting", ALICE.getName());
        Reminder reminder8 = new Reminder("02-03-2022", "meeting", ALICE.getName());
        assertTrue(reminder7.isSameReminder(reminder8));

        // name has trailing spaces, all other attributes same -> returns false
        Reminder reminder9 = new Reminder("02-03-2022", "meeting", new Name(VALID_NAME_BOB));
        Reminder reminder10 = new Reminder("02-03-2022", "meeting", new Name(VALID_NAME_BOB + " "));
        assertFalse(reminder9.isSameReminder(reminder10));
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

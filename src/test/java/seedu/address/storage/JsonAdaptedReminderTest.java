package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.Reminder;

public class JsonAdaptedReminderTest {
    private static final String VALID_DATE = "01-01-2024";
    private static final String VALID_DESCRIPTION = "valid description";
    private static final Name VALID_NAME = BENSON.getName();
    private static final Reminder VALID_REMINDER = new Reminder(VALID_DATE, VALID_DESCRIPTION, VALID_NAME);
    @Test
    public void toModelType_validReminderDetails_returnsReminder() throws Exception {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_REMINDER);
        assertEquals(VALID_REMINDER, reminder.toModelType());
    }
}

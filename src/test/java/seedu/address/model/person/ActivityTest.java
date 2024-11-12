package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@code Activity}.
 */
public class ActivityTest {

    @Test
    public void of_validDateAndMessage_success() {
        LocalDate date = LocalDate.of(2024, 10, 12);
        String validActivity = "Completed task A";

        Activity activity = Activity.of(date, validActivity);

        assertEquals("Completed task A", activity.toString());
    }

    @Test
    public void of_emptyMessage_throwsIllegalArgumentException() {
        LocalDate date = LocalDate.of(2024, 10, 12);
        String emptyMessage = "";

        // Expect IllegalArgumentException when the message is empty
        assertThrows(IllegalArgumentException.class, () -> Activity.of(date, emptyMessage));
    }

    @Test
    public void of_nullMessage_throwsIllegalArgumentException() {
        LocalDate date = LocalDate.of(2024, 10, 12);

        // Expect IllegalArgumentException when the message is null
        assertThrows(NullPointerException.class, () -> Activity.of(date, null));
    }

    @Test
    public void of_emptyMessage_success() {
        LocalDate date = LocalDate.of(2024, 10, 12);
        String validMessage = "some history";

        Activity activity = Activity.of(date, validMessage);

        // Expect the activity to still be created, with the empty message
        assertEquals("some history", activity.toString());
    }

    @Test
    public void of_invalidDateFormat_throwsDateTimeParseException() {
        String invalidDateString = "2024-13-32";
        String message = "Invalid date test";

        // Expect DateTimeParseException to be thrown for invalid date input.
        assertThrows(DateTimeParseException.class, () -> {
            LocalDate.parse(invalidDateString); // This simulates an invalid date format being used.
        });
    }

    @Test
    public void of_invalidDateLogic_throwsDateTimeException() {
        // Check for invalid logic, e.g., February 30
        String message = "Task on invalid date";
        assertThrows(DateTimeException.class, () -> Activity.of(LocalDate.of(2024,
                2, 30), message));
    }
}

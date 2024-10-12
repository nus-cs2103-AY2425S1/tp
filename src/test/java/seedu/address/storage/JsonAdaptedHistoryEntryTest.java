package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@code JsonAdaptedHistoryEntry}.
 */
public class JsonAdaptedHistoryEntryTest {

    @Test
    public void constructor_withValidDateAndActivities_success() {
        // Valid date and activities
        LocalDate date = LocalDate.of(2024, 10, 12);
        List<String> activities = Arrays.asList("Completed task A", "Meeting with client");

        // Create a JsonAdaptedHistoryEntry instance
        JsonAdaptedHistoryEntry entry = new JsonAdaptedHistoryEntry(date, activities);

        // Verify that the date and activities are correctly stored
        assertEquals("2024-10-12", entry.toDate().toString());
        assertEquals(activities, entry.getActivities());
    }

    @Test
    public void constructor_withStringDate_success() {
        // Valid date as a string and activities
        String dateString = "2024-10-12";
        List<String> activities = Arrays.asList("Completed task A", "Meeting with client");

        // Create a JsonAdaptedHistoryEntry instance using the string constructor
        JsonAdaptedHistoryEntry entry = new JsonAdaptedHistoryEntry(dateString, activities);

        // Verify that the date string is correctly converted back to LocalDate
        assertEquals(LocalDate.of(2024, 10, 12), entry.toDate());
        assertEquals(activities, entry.getActivities());
    }

    @Test
    public void toDate_invalidDateFormat_throwsDateTimeParseException() {
        // Invalid date format
        String invalidDateString = "2024-31-12"; // Invalid date

        // Expect DateTimeParseException when the date string is invalid
        assertThrows(java.time.format.DateTimeParseException.class, () -> {
            JsonAdaptedHistoryEntry entry = new JsonAdaptedHistoryEntry(invalidDateString, Arrays.asList("Task A"));
            entry.toDate(); // This should trigger the exception
        });
    }

    @Test
    public void getActivities_validActivitiesList_success() {
        // Valid date and activities
        LocalDate date = LocalDate.of(2024, 10, 12);
        List<String> activities = Arrays.asList("Task A", "Task B");

        // Create a JsonAdaptedHistoryEntry instance
        JsonAdaptedHistoryEntry entry = new JsonAdaptedHistoryEntry(date, activities);

        // Verify the activities are correctly stored
        assertEquals(activities, entry.getActivities());
    }

    @Test
    public void getActivities_emptyActivitiesList_success() {
        // Valid date with an empty activities list
        LocalDate date = LocalDate.of(2024, 10, 12);
        List<String> activities = Arrays.asList();

        // Create a JsonAdaptedHistoryEntry instance
        JsonAdaptedHistoryEntry entry = new JsonAdaptedHistoryEntry(date, activities);

        // Verify the activities are correctly stored
        assertEquals(activities, entry.getActivities());
    }

    @Test
    public void constructor_withEmptyActivitiesList_success() {
        // Valid date and empty activities list
        LocalDate date = LocalDate.of(2024, 10, 12);
        List<String> emptyActivities = new ArrayList<>();

        // Create a JsonAdaptedHistoryEntry instance
        JsonAdaptedHistoryEntry entry = new JsonAdaptedHistoryEntry(date, emptyActivities);

        // Verify the activities list is empty
        assertEquals(emptyActivities, entry.getActivities());
    }
}

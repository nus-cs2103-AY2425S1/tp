package seedu.address.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Jackson-friendly version of a History entry.
 * This class is used to store a date and a list of activities associated with that date
 * in a format that can be serialized and deserialized to and from JSON.
 */
public class JsonAdaptedHistoryEntry {

    // Stores the date as a String in the format "YYYY-MM-DD"
    private final String date;

    // A list of activities associated with the specified date
    private final List<String> activities;

    /**
     * Constructs a {@code JsonAdaptedHistoryEntry} with the given details.
     *
     * @param date The date for this history entry, stored as a String.
     * @param activities A list of activities that occurred on this date.
     */
    @JsonCreator
    public JsonAdaptedHistoryEntry(@JsonProperty("date") String date,
                                   @JsonProperty("activities") List<String> activities) {
        this.date = date;
        this.activities = activities;
    }

    /**
     * Constructs a {@code JsonAdaptedHistoryEntry} from a {@code LocalDate} and a list of activities.
     *
     * @param date The {@code LocalDate} for the history entry.
     * @param activities A list of activities that occurred on the given date.
     */
    public JsonAdaptedHistoryEntry(LocalDate date, List<String> activities) {
        this.date = date.toString();
        this.activities = new ArrayList<>(activities);
    }

    /**
     * Converts the stored date string back into a {@code LocalDate}.
     *
     * @return A {@code LocalDate} representing the stored date.
     */
    public LocalDate toDate() {
        return LocalDate.parse(date); // Convert the String back to LocalDate
    }

    /**
     * Returns the list of activities for this history entry.
     *
     * @return A list of activities associated with the stored date.
     */
    public List<String> getActivities() {
        return activities;
    }
}

package seedu.address.model.appointment;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for handling time-related operations for appointments.
 */
public class AppointmentUtil {

    public static final String TIME_MESSAGE_CONSTRAINTS =
            "Times should be in the format HH:mm or HHmm, e.g., 0900 or 09:00.";

    /**
     * Parses the provided time string into a {@code LocalTime} object.
     * Accepts both HH:mm and HHmm formats.
     *
     * @param time the time string to parse
     * @return the parsed {@code LocalTime} object
     * @throws IllegalArgumentException if the provided time string is not in a valid format
     */
    public static LocalTime parseTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            if (time.matches("\\d{4}")) {
                time = time.substring(0, 2) + ":" + time.substring(2);
            }
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(TIME_MESSAGE_CONSTRAINTS);
        }
    }
}

package seedu.address.model.types.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Utility class for handling date and time operations.
 */
public class DateTimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Gets the current date and time as a {@link LocalDateTime} object.
     *
     * @return The current date and time as a formatted {@link LocalDateTime} object.
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * Gets the current date and time formatted as a string.
     *
     * @return The current date and time as a formatted string in the pattern "yyyy-MM-dd HH:mm".
     */
    public static String getCurrentDateTimeString() {
        return getCurrentDateTime().format(DATE_TIME_FORMATTER);
    }

    /**
     * Parses a date and time string into a {@link LocalDateTime} object.
     *
     * @param dateTimeString The date and time string to be parsed. It must be in the format "yyyy-MM-dd HH:mm".
     * @return The parsed {@link LocalDateTime} object corresponding to the given string.
     */
    public static LocalDateTime parse(String dateTimeString) {
        if (!DateTime.isValidDateTime(dateTimeString)) {
            throw new IllegalArgumentException("Invalid dateTime format");
        }
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    }

    /**
     * Creates a Timeline that updates at a specified interval.
     *
     * @param action The action to perform on each update.
     * @return The configured Timeline.
     */
    public static Timeline createTimeline(Runnable action) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> action.run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }
}

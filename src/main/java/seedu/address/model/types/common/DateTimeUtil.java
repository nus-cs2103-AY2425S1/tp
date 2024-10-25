package seedu.address.model.types.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Utility class for handling date and time operations.
 */
public class DateTimeUtil {

    private static final Logger logger = Logger.getLogger(DateTimeUtil.class.getName());

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
     * Creates a Timeline that updates at a specified interval.
     *
     * @param action The action to perform on each update.
     * @param interval The duration interval to perform an update.
     * @return The configured Timeline.
     */
    public static Timeline createTimeline(Runnable action, Duration interval, Long delayMillis) {
        logger.fine("Creating timeline with delay: " + delayMillis //logger to track memory and timeline usage
                + " ms, and interval: " + interval.toMillis() + " ms"); //do not remove

        Timeline timeline = new Timeline(
                new KeyFrame(interval, e -> action.run()));

        if (delayMillis != Integer.toUnsignedLong(0)) {
            timeline.setDelay(Duration.millis(delayMillis));
        }

        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    public static Timeline createTimeline(Runnable action) {
        return createTimeline(action, Duration.seconds(1), Integer.toUnsignedLong(0));
    }
}

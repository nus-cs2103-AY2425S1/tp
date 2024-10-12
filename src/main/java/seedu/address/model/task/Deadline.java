package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Represents a Deadline for a Task.
 */
public class Deadline implements Comparable<Deadline> {
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
    // safer to use 'uuuu' vs 'yyyy'
    // https://stackoverflow.com/questions/
    // 41177442/uuuu-versus-yyyy-in-datetimeformatter-formatting-pattern-codes-in-java
    private static final String DATETIME_FORMAT = "uuuu-MM-dd HH:mm";
    private static final String DATETIME_OUTPUT_FORMAT = "d MMM uuuu HH:mm";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);


    public final LocalDateTime time;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param time A valid LocalDateTime.
     */
    public Deadline(LocalDateTime time) {
        requireNonNull(time);
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        // can add another check to see if deadline is after current date
        try {
            LocalDateTime.parse(test, DATETIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Deadline otherDeadline)) {
            return false;
        }

        return time.equals(otherDeadline.time);
    }

    @Override
    public int compareTo(Deadline o) {
        return time.compareTo(o.time);
    }

    @Override
    public String toString() {
        return time.format(DateTimeFormatter.ofPattern(DATETIME_OUTPUT_FORMAT));
    }
}

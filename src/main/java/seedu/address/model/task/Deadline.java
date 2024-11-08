package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

//@@author gho7sie
/**
 * Represents a Deadline for a Task.
 */
public class Deadline implements Comparable<Deadline> {
    public static final String MESSAGE_CONSTRAINTS = "Deadlines must be given in the form YYYY-MM-DD HHmm.\n";
    public static final String MESSAGE_INCOMPLETE = "Provided deadline is incomplete.";
    public static final String MESSAGE_EXCESSIVE = "Provided deadline has excessive parts.";
    public static final String MESSAGE_INVALID = "Provided deadline is invalid.";
    public static final String VALIDATION_REGEX = "(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2}) (?<hour>\\d{2})"
        + "(?<minute>\\d{2})";
    // safer to use 'uuuu' vs 'yyyy'
    // https://stackoverflow.com/questions/
    // 41177442/uuuu-versus-yyyy-in-datetimeformatter-formatting-pattern-codes-in-java
    public static final String DATETIME_FORMAT = "uuuu-MM-dd HHmm";
    public static final String DATETIME_OUTPUT_FORMAT = "d MMM uuuu HHmm";
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter
        .ofPattern(DATETIME_FORMAT)
        .withResolverStyle(ResolverStyle.STRICT);


    private final LocalDateTime time;

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
     * Represents an empty constructor.
     */
    public Deadline() {
        this.time = LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
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

    public String deadlineInInputFormat() {
        return time.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }
}

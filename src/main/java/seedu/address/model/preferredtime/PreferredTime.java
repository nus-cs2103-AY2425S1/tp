package seedu.address.model.preferredtime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a Person's preferred time to play games in the gamer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPreferredTime(String)}
 */
public class PreferredTime {

    public static final String MESSAGE_CONSTRAINTS =
            "PreferredTime should consists of start and end Time in the format 'HHmm-HHmm'.\n"
            + "Time should be in range from 0000 to 2359.";
    public static final String VALIDATION_REGEX = "(\\d{4})-(\\d{4})$";
    public static final String TIME_CHECK = "^([01][0-9]|2[0-3])[0-5][0-9]$";
    public static final Pattern VALIDATED_PATTERN = Pattern.compile(VALIDATION_REGEX);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");


    public final String preferredTime;
    public final LocalTime start;
    public final LocalTime end;


    /**
     * Constructs a {@code PreferredTime}.
     *
     * @param preferredTime A valid preferredTime input that can be break down to valid day and valid time.
     */
    public PreferredTime(String preferredTime) {
        requireNonNull(preferredTime);
        checkArgument(isValidPreferredTime(preferredTime), MESSAGE_CONSTRAINTS);

        this.preferredTime = preferredTime;
        Matcher matcher = VALIDATED_PATTERN.matcher(preferredTime); // should always match as checked
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid preferred time format: " + preferredTime);
        }

        start = LocalTime.parse(matcher.group(1), TIME_FORMATTER);
        end = LocalTime.parse(matcher.group(2), TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid PreferredTime.
     */
    public static boolean isValidPreferredTime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        Matcher matcher = VALIDATED_PATTERN.matcher(test); // should always match as checked
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid preferred time format: " + test);
        }

        String start = matcher.group(1);
        String end = matcher.group(2);

        Matcher matchStart = Pattern.compile(TIME_CHECK).matcher(start);
        Matcher matchEnd = Pattern.compile(TIME_CHECK).matcher(end);

        if (!matchStart.matches() || !matchEnd.matches()) {
            return false;
        }
        return LocalTime.parse(start, TIME_FORMATTER).isBefore(LocalTime.parse(end, TIME_FORMATTER));
    }

    public boolean overlaps(PreferredTime other) {
        return !(end.isBefore(other.start) || start.isAfter(other.end));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PreferredTime)) {
            return false;
        }

        PreferredTime otherPreferredTime = (PreferredTime) other;
        return preferredTime.equals(otherPreferredTime.preferredTime);
    }

    @Override
    public int hashCode() {
        return preferredTime.hashCode();
    }


    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + preferredTime + ']';
    }
}

package seedu.address.model.preferredtime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a Person's preferred time to play games in the gamer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPreferredTime(String)}
 */
public class PreferredTime {

    public static final String MESSAGE_CONSTRAINTS =
            "PreferredTime should consists of Day and Time in the format 'Day HHmm'.\n"
            + "There should be exactly one space in between";
    public static final String VALIDATION_REGEX = "(\\p{L}+)\\s(\\d{4})$";
    private static final Pattern VALIDATED_PATTERN = Pattern.compile(VALIDATION_REGEX);

    public final String preferredTime;
    public final Day day;
    public final Time time;


    // TODO: constructor
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

        day = new Day(matcher.group(1));
        time = new Time(matcher.group(2));
    }

    /**
     * Returns true if a given string is a valid PreferredTime.
     */
    public static boolean isValidPreferredTime(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return day.equals(otherPreferredTime.day) && time.equals(otherPreferredTime.time);
    }

    @Override
    public int hashCode() {
        return preferredTime.hashCode();
    }


    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + day.toString() + ' ' + time.toString() + ']';
    }
}

package seedu.address.model.preferredtime;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's preferred time to play games in the gamer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPreferredTime(String)}
 */
public class PreferredTime {

    public static final String MESSAGE_CONSTRAINTS =
            "PreferredTime should consists of Day and Time in the format 'Day HHmm'.\n"
            + "There should be exactly one space in between";

    // TODO: change the REGEX
    public static final String VALIDATION_REGEX = "\\p{L}+\\s\\d{4}$";
    public final String preferredTime;

    // TODO: need to change to Day and Time separately in the future
    public final Day day;
    public final Time time;


    // TODO: constructor
    /**
     * Constructs a {@code PreferredTime}.
     *
     * @param day
     * @param time
     */
    public PreferredTime(String preferredTime, String day, String time) {
        requireNonNull(day, time);
        this.preferredTime = preferredTime;
        this.day = new Day(day);
        this.time = new Time(time);
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

    // TODO: toString
    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + day.toString() + ' ' + time.toString() + ']';
    }
}

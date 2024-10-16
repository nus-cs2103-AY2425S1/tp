package seedu.address.model.preferredtime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Day of PreferredTime to play games in the gamer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}.
 */
public class Day {

    public static final String MESSAGE_CONSTRAINTS =
            "Day should only contain alphanumeric characters and it should not be blank.\n"
            + "It should be one of the {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}.\n"
            + "Or corresponding abbreviations {Mon, Tue, Wed, Thu, Fri, Sat, Sun}";
    public static final String REGEX_DAY_COMPLETE = "Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday";
    public static final String REGEX_DAY_ABBR = "Mon|Tue|Wed|Thu|Fri|Sat|Sun";
    public static final String VALIDATION_REGEX = "^(?i)(" + REGEX_DAY_COMPLETE + "|"
            + REGEX_DAY_ABBR + ")$";

    public final String day;

    /**
     * Constructs a {@code Day}.
     *
     * @param day A valid day.
     */
    public Day(String day) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_CONSTRAINTS);
        this.day = day;
    }


    /**
     * Returns true if a given string is a valid Day.
     */
    public static boolean isValidDay(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    // TODO: equals
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return day.equals(otherDay.day);
    }

    @Override
    public int hashCode() {
        return day.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return day;
    }
}

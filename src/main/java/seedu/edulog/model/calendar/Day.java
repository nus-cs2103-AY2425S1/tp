package seedu.edulog.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Denotes the start day of a Lesson, which is displayed in the Calendar.
 */
public class Day {
    // Denotes the valid days of the week, spelt in full.
    public static final List<String> DAYS_OF_THE_WEEK = new ArrayList<String>(
        List.of("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"));

    // 3-letter shorthands of the days of the week
    public static final List<String> DAYS_OF_THE_WEEK_SHORTHAND =
        DAYS_OF_THE_WEEK.stream().map(s -> s.substring(0, 3)).collect(Collectors.toList());

    public static final String INVALID_DAY_OF_WEEK =
        "Day of the week must be spelt as "
            + "'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', or 'Sunday'"
            + "or its 3-letter shorthands, example 'Mon' (non case-sensitive).";


    public final DayOfWeek day;

    /**
     * Accepts String representations of a day of the calendar week. <br>
     * Users can provide the day of the week in full, e.g. "Monday", "Saturday". <br>
     * Experienced users may use the respective 3-letter shorthands to reduce typing required, e.g. "Tue" or "Fri". <br>
     * In both cases, the check is non-case sensitive.
     * @param day String representation of any day in the calendar week, e.g. "Monday" or "Wed".
     */
    public Day(String day) {
        requireNonNull(day);
        checkArgument(checkValidDayOfWeek(day), INVALID_DAY_OF_WEEK);

        this.day = processDayOfWeek(day);
    }

    // Validator and parsing fns ================================================================================

    /**
     * Checks if the day of week matches any of the 7 days of the week of their 3-letter shorthands,
     * example "Wednesday" or "fri".
     * Non-case sensitive.
     */
    public static boolean checkValidDayOfWeek(String dayOfWeek) {
        return DAYS_OF_THE_WEEK.contains(dayOfWeek.toLowerCase())
            || DAYS_OF_THE_WEEK_SHORTHAND.contains(dayOfWeek.toLowerCase());
    }

    /**
     * Generates a DayOfWeek based on a String representation.
     * Callers are advised to use {@link #checkValidDayOfWeek(String)} first and handle abnormal use cases themselves.
     * Otherwise, an error message is generated that may be visible to the user.
     */
    private static DayOfWeek processDayOfWeek(String dayOfWeek) {
        checkArgument(checkValidDayOfWeek(dayOfWeek), INVALID_DAY_OF_WEEK);
        switch (dayOfWeek.toLowerCase()) {
        case "monday": case "mon":
            return DayOfWeek.MONDAY;
        case "tuesday": case "tue":
            return DayOfWeek.TUESDAY;
        case "wednesday": case "wed":
            return DayOfWeek.WEDNESDAY;
        case "thursday": case "thu":
            return DayOfWeek.THURSDAY;
        case "friday": case "fri":
            return DayOfWeek.FRIDAY;
        case "saturday": case "sat":
            return DayOfWeek.SATURDAY;
        case "sunday": case "sun":
            return DayOfWeek.SUNDAY;
        default:
            // because this is prior to a validity check, this use of an unchecked runtime exception is
            // warranted here as it denotes a gross failure of internal data processing.
            throw new RuntimeException("Something went horribly wrong when parsing a lesson time.");
        }
    }

    // Utility fns ===================================================================================================

    /**
     * Adds a selected number of days to the current day of the week.
     * Example: adding 2 days to Wednesday gives a Friday. <br>
     * This is a non-destructive function - a new day is generated without affecting the original.
     * @param i the number of days to add to the current day of the week
     * @return A new copy of Day with the requisite number of days added.
     */
    public Day plus(int i) {
        return new Day(day.plus(i).toString());
    }

    // Printing and identity fns ====================================================================================

    @Override
    public String toString() {
        return day.toString();
    }

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
        return Objects.hash(day);
    }
}

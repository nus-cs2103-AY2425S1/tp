package keycontacts.model.lesson;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.util.Map;

/**
 * Represents a Student's lesson day in the student directory.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}
 */
public class Day {

    public static final String MESSAGE_CONSTRAINTS =
            "Days should be one of the days of the week (e.g. Monday, Tuesday etc.), or its common three letter"
                    + " abbreviation (e.g. Mon, Tue etc.)";

    /*
     * Either day of the week, or three letter abbreviation, case-insensitive.
     */
    public static final String VALIDATION_REGEX =
            "(?i)^(mon|tue|wed|thu|fri|sat|sun|monday|tuesday|wednesday|thursday|friday|saturday|sunday)$";

    // Map abbreviations to full day names
    private static final Map<String, String> ABBREVIATION_TO_DAY_MAP = Map.of(
            "mon", "MONDAY",
            "tue", "TUESDAY",
            "wed", "WEDNESDAY",
            "thu", "THURSDAY",
            "fri", "FRIDAY",
            "sat", "SATURDAY",
            "sun", "SUNDAY"
    );

    public final DayOfWeek value;

    /**
     * Constructs a {@code Day}.
     *
     * @param day A valid day.
     */
    public Day(String day) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_CONSTRAINTS);
        this.value = convertToDayOfWeek(day);
    }

    /**
     * Converts the day input into a {@link DayOfWeek} enum.
     */
    private DayOfWeek convertToDayOfWeek(String day) {
        String lowerCaseDay = day.toLowerCase();
        String fullDay = ABBREVIATION_TO_DAY_MAP.getOrDefault(lowerCaseDay, lowerCaseDay.toLowerCase());
        return DayOfWeek.valueOf(fullDay.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid day.
     */
    public static boolean isValidDay(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
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
        return value.equals(otherDay.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

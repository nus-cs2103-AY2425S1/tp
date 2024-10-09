package seedu.address.model.calendar;

import java.time.format.DateTimeFormatter;

/**
 * Represents the 7 possible days "Monday, Tuesday, ..." in a calendar week.
 */
public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}

package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.student.Days.isValidDay;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;


/**
 * Represents a Student's lesson time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSchedule(String)}
 */
public class Schedule {
    public static final String MESSAGE_CONSTRAINTS = """
            Schedule should be in the format of Day-startTime-endTime
            and adhere to the following constraints:
            1. Day should be a valid day of the week:
            Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
            2. startTime and endTime should be in the format of HHMM
            3. startTime should be strictly before endTime
            4. startTime and endTime should be within the range of 0000 to 2359
            There should be no whitespace in between the day, startTime and endTime
            """;

    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");

    public final Days dayValue;

    public final LocalTime startTimeValue;

    public final LocalTime endTimeValue;

    public final String value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Schedule(String time) {
        requireNonNull(time);
        checkArgument(isValidSchedule(time), MESSAGE_CONSTRAINTS);
        this.value = time;
        this.dayValue = getDayComponent(time);
        this.startTimeValue = getStartTimeComponent(time);
        this.endTimeValue = getEndTimeComponent(time);
    }

    /**
     * Returns true if a given string is a valid Schedule.
     */
    public static boolean isValidSchedule(String test) {
        String[] split = test.split("-");
        if (split.length != 3) {
            return false;
        }
        String day = split[0];
        String startTime = split[1];
        String endTime = split[2];

        boolean isValidDayFormat = isValidDay(day);
        boolean isValidTimeFormat = isValidTime(startTime) && isValidTime(endTime);
        boolean isValidTimeRange = isValidTimeRange(startTime, endTime);
        return isValidDayFormat && isValidTimeFormat && isValidTimeRange;
    }


    /**
     * Returns true if the time string is a valid time
     */
    public static boolean isValidTime(String time) {
        if (time.length() != 4) {
            return false;
        }

        // disallow 2400
        if (time.equals("2400")) {
            return false;
        }

        try {
            LocalTime.parse(time, TIME_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }


    /**
     * Returns true if startTime is before endTime
     */
    public static boolean isValidTimeRange(String startTime, String endTime) {
        if (!isValidTime(startTime) || !isValidTime(endTime)) {
            return false;
        }

        LocalTime start = LocalTime.parse(startTime, TIME_FORMAT);
        LocalTime end = LocalTime.parse(endTime, TIME_FORMAT);

        return start.isBefore(end);
    }

    /**
     * Determines whether there is a clash between this schedule and another schedule.
     * @param otherSchedule The schedule to compare against for clashes.
     * @return True if this schedule clashes with the specified otherSchedule.
     */
    public boolean isClash(Schedule otherSchedule) {
        requireNonNull(otherSchedule);
        return dayValue == otherSchedule.dayValue
                && startTimeValue.isBefore(otherSchedule.endTimeValue)
                && otherSchedule.startTimeValue.isBefore(endTimeValue);
    }

    /**
     * Determines whether this schedule is on the specified day.
     * @param day The day to check against.
     * @return True if this schedule is on the specified day.
     */
    public boolean isOn(Days day) {
        return dayValue == day;
    }

    @Override
    public String toString() {
        return dayValue + " " + startTimeValue + " - " + endTimeValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return dayValue.equals(otherSchedule.dayValue)
                && startTimeValue.equals(otherSchedule.startTimeValue)
                && endTimeValue.equals(otherSchedule.endTimeValue);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(dayValue, startTimeValue, endTimeValue);
    }

    private Days getDayComponent(String time) {
        String[] split = time.split("-");
        return Days.valueOf(split[0].toUpperCase());
    }

    private LocalTime getStartTimeComponent(String time) {
        String[] split = time.split("-");
        return LocalTime.parse(split[1], TIME_FORMAT);
    }

    private LocalTime getEndTimeComponent(String time) {
        String[] split = time.split("-");
        return LocalTime.parse(split[2], TIME_FORMAT);
    }

    public String getTime() {
        return startTimeValue.toString() + " - " + endTimeValue.toString();
    }
}


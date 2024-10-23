package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Person's schedule in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}, {@link #isValidDate(String)},
 * {@link #isValidTime(String)}
 */
public class Schedule {

    public static final String SCHEDULE_NAME_CONSTRAINTS =
            "Schedule name field must be strictly alphanumeric.";
    public static final String DATE_CONSTRAINTS =
            "Date field must be given in the format yyyy-MM-dd.";
    public static final String TIME_CONSTRAINTS =
            "Time field must be given in the format HH:mm.";

    public static final String NAME_VALIDATION_REGEX = "\\p{Alnum}+";

    public final LocalDate date;
    public final LocalTime time;
    public final String scheduleName;
    public final String dateString;
    public final String timeString;

    /**
     * Constructs a {@code Schedule}
     *
     * @param scheduleName Name of schedule, if any
     * @param date Date of Schedule, given in yyyy-MM-dd format
     * @param time Time of Schedule, given in HH:mm format, if any
     */
    public Schedule(String scheduleName, String date, String time) {
        requireAllNonNull(scheduleName, date, time);

        checkArgument(isValidName(scheduleName), SCHEDULE_NAME_CONSTRAINTS);
        checkArgument(isValidDate(date), DATE_CONSTRAINTS);
        checkArgument(isValidTime(time), TIME_CONSTRAINTS);

        this.date = (date.isEmpty()) ? LocalDate.MIN : LocalDate.parse(date);
        this.time = (time.isEmpty()) ? LocalTime.MIDNIGHT : LocalTime.parse(time);

        this.scheduleName = (scheduleName.isEmpty()) ? "schedule" : scheduleName;
        this.dateString = date;
        this.timeString = time;
    }

    /**
     * Returns true if a given string is a valid schedule name or empty.
     */
    public static boolean isValidName(String test) {
        return test.isEmpty() || test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid schedule date or empty.
     */
    public static boolean isValidDate(String test) {
        if (test.isEmpty()) {
            return true;
        }
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid schedule time or empty.
     */
    public static boolean isValidTime(String test) {
        if (test.isEmpty()) {
            return true;
        }
        try {
            LocalTime.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public LocalDateTime getDateTime() {
        if (this.date.equals(LocalDate.MIN)) {
            return null;
        }

        return LocalDateTime.of(this.date, this.time);
    }

    @Override
    public String toString() {
        if (this.date.equals(LocalDate.MIN)) {
            return "";
        }
        String scheduleString = this.scheduleName + ": " + this.dateString;
        if (!this.timeString.isEmpty()) {
            scheduleString += " " + this.timeString;
        }
        return scheduleString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;

        return (this.dateString.isEmpty() && otherSchedule.dateString.isEmpty())
                || (this.scheduleName.equals(otherSchedule.scheduleName)
                && this.date.equals(otherSchedule.date)
                && this.time.equals(otherSchedule.time));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.scheduleName, this.date, this.time);
    }
}

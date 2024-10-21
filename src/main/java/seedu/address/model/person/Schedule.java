package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Schedule {

    public static final String DATE_CONSTRAINTS =
            "Date field must be given in the format yyyy-MM-dd.";
    public static final String TIME_CONSTRAINTS =
            "Time field must be given in the format HH:mm.";

    public final LocalDate date;
    public final LocalTime time;
    public final String dateString;
    public final String timeString;

    public static Schedule ofDefault() {
        return new Schedule("", "");
    }

    public Schedule(String date, String time) {
        requireAllNonNull(date, time);

        checkArgument(isValidDate(date), DATE_CONSTRAINTS);
        checkArgument(isValidTime(time), TIME_CONSTRAINTS);

        this.date = (date.isEmpty()) ? LocalDate.MIN : LocalDate.parse(date);
        this.time = (time.isEmpty()) ? LocalTime.MIDNIGHT : LocalTime.parse(time);

        this.dateString = (date.isEmpty()) ? LocalDate.MIN.toString() : date;
        this.timeString = (time.isEmpty()) ? LocalTime.MIDNIGHT.toString() : time;
    }

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

    @Override
    public String toString() {
        return this.date.toString() + " " + this.time.toString();
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
        return this.date.equals(otherSchedule.date)
                && this.time.equals(otherSchedule.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.date, this.time);
    }
}

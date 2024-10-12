package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Time {
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
    public final LocalTime time;

    public static final String MESSAGE_CONSTRAINTS =
            "Time should follow the format <HHmm>";

    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time, formatter);
    }

    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

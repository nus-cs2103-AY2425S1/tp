package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Incorrect time format. Expected format: hh:mm:ss";
    public final LocalTime value;

    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidEta(time), MESSAGE_CONSTRAINTS);
        value = LocalTime.parse(time);
    }

    public static boolean isValidEta(String test) {
        try {
            LocalTime parsedTime = LocalTime.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Time Ordered: " + value;
    }
}

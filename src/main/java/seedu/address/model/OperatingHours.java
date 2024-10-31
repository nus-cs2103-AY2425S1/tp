package seedu.address.model;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.parseDateTime;
import static seedu.address.logic.parser.ParserUtil.parseTime;

import java.time.LocalTime;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.exceptions.TimeParseException;

/**
 * Contains the opening and closing hours of the clinic.
 * Default opening and closing hours are 00:00 and 23:59 respectively.
 */
public class OperatingHours {
    public static final String MESSAGE_CONSTRAINTS = "Both opening time and closing time should be in format HH:mm.";
    private static final LocalTime DEFAULT_OPENING_HOURS = LocalTime.of(0, 0);
    private static final LocalTime DEFAULT_CLOSING_HOURS = LocalTime.of(23, 59);

    private final LocalTime openingHour;
    private final LocalTime closingHour;

    /**
     * Creates default operating hours
     */
    public OperatingHours() {
        this.openingHour = DEFAULT_OPENING_HOURS;
        this.closingHour = DEFAULT_CLOSING_HOURS;
    }

    /**
     * Creates operating hours with given string
     */
    public OperatingHours(String source) {
        checkArgument(isValidOperatingHours(source), MESSAGE_CONSTRAINTS);

        String[] tmp = source.split(" to ", 2);

        if (tmp.length != 2) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        try {
            this.openingHour = parseTime(tmp[0]);
            this.closingHour = parseTime(tmp[1]);
        } catch (ParseException e) {
            throw new TimeParseException();
        }
    }

    /**
     * Creates operating hours given {@code openingHour} and {@code closingHour}
     */
    public OperatingHours(LocalTime openingHour, LocalTime closingHour) {
        if (openingHour == null) {
            this.openingHour = DEFAULT_OPENING_HOURS;
        } else {
            this.openingHour = openingHour;
        }

        if (closingHour == null) {
            this.closingHour = DEFAULT_CLOSING_HOURS;
        } else {
            this.closingHour = closingHour;
        }
    }

    /**
     * Checks if {@code appointment} timing is within operating hours
     */
    public boolean isWithinOperatingHours(Appointment appointment) {
        try {
            LocalTime dateTime = parseDateTime(appointment.dateTime).toLocalTime();
            System.out.println(dateTime.isBefore(this.closingHour) && dateTime.isAfter(this.openingHour));
            return dateTime.isBefore(this.closingHour) && dateTime.isAfter(this.openingHour);

        } catch (TimeParseException e) {
            // if patient has no appointments
            return true;
        }

    }

    /**
     * Checks if all appointments are within operating hours
     */
    public boolean isCalenderValid(List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (!isWithinOperatingHours(appointment)) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if a given string a valid OperatingHours
     */
    public static boolean isValidOperatingHours(String test) {
        String[] tmp = test.split(" to ", 2);
        try {
            parseTime(tmp[0]);
            parseTime(tmp[1]);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns opening hour
     */
    public LocalTime getOpeningHour() {
        return openingHour;
    }

    /**
     * Returns closing hour
     */
    public LocalTime getClosingHour() {
        return closingHour;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OperatingHours)) {
            return false;
        }

        OperatingHours otherOperatingHours = (OperatingHours) other;
        return otherOperatingHours.openingHour.equals(this.openingHour)
                && otherOperatingHours.closingHour.equals(this.closingHour);
    }

    @Override
    public String toString() {
        // to be changed
        return openingHour.toString() + " to " + closingHour.toString();
    }
}

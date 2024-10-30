package seedu.address.model;

import static seedu.address.logic.parser.ParserUtil.parseDateTime;

import java.time.LocalTime;
import java.util.List;

import seedu.address.model.person.Appointment;
import seedu.address.model.person.exceptions.TimeParseException;




/**
 * Contains the opening and closing hours of the clinic.
 * Default opening and closing hours are 00:00 and 23:59 respectively.
 */
public class OperatingHours {
    private static final LocalTime DEFAULT_OPENING_HOURS = LocalTime.of(0, 0);
    private static final LocalTime DEFAULT_CLOSING_HOURS = LocalTime.of(23, 59);

    private LocalTime openingHour;
    private LocalTime closingHour;

    /**
     * Creates default operating hours
     */
    public OperatingHours() {
        this.openingHour = DEFAULT_OPENING_HOURS;
        this.closingHour = DEFAULT_CLOSING_HOURS;
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
     * Checks if all appointments are within operting hours
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
        return openingHour.toString() + " " + closingHour.toString();
    }
}

package seedu.address.model;

import seedu.address.model.person.Appointment;
import seedu.address.model.person.exceptions.TimeParseException;

import java.time.LocalTime;
import java.util.List;

import static seedu.address.logic.parser.ParserUtil.parseDateTime;

public class OperatingHours {
    private static final LocalTime DEFAULT_OPENING_HOURS = LocalTime.of(0, 0);
    private static final LocalTime DEFAULT_CLOSING_HOURS = LocalTime.of(23, 59);

    public LocalTime openingHour;
    public LocalTime closingHour;

    public OperatingHours() {
        this.openingHour = DEFAULT_OPENING_HOURS;
        this.closingHour = DEFAULT_CLOSING_HOURS;
    }

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

    public boolean isValid(Appointment appointment) {
        try {
            LocalTime dateTime = parseDateTime(appointment.dateTime).toLocalTime();
            System.out.println(dateTime.isBefore(this.closingHour) && dateTime.isAfter(this.openingHour));
            return dateTime.isBefore(this.closingHour) && dateTime.isAfter(this.openingHour);

        } catch (TimeParseException e) {
            // if patient has no appointments
            return true;
        }

    }

    public boolean isCalenderValid(List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (!isValid(appointment)) {
                return false;
            }
        }
        return true;
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

package careconnect.model.person;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import careconnect.commons.util.AppUtil;
import careconnect.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's Appointment Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentDateString(String)}
 */
public class AppointmentDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Appointment Dates must be valid and in the format yyyy-MM-dd";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final String VALIDATION_REGEX = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

    public final Optional<Date> date;

    /**
     * Constructs an {@code AppointmentDate}.
     *
     * @param dateString to create date from
     * @throws ParseException if unable to parse into date
     */
    public AppointmentDate(String dateString) throws ParseException {
        requireNonNull(dateString);
        AppUtil.checkArgument(isValidAppointmentDateString(dateString), MESSAGE_CONSTRAINTS);
        assert (!DATE_FORMAT.isLenient());
        try {
            this.date = Optional.of(DATE_FORMAT.parse(dateString));
        } catch (java.text.ParseException e) {
            throw new ParseException(MESSAGE_CONSTRAINTS, e);
        }
    }

    /**
     * Constructs an empty {@code AppointmentDate}.
     */
    public AppointmentDate() {
        this.date = Optional.empty();
    }

    public Optional<Date> getDate() {
        return date;
    }

    public static boolean isValidAppointmentDateString(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getDateString() {
        return date.map(DATE_FORMAT::format).orElse("");
    }

    @Override
    public String toString() {
        return getDateString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentDate // instanceof handles nulls
                && date.equals(((AppointmentDate) other).date)); // state check
    }
    @Override
    public int hashCode() {
        return date.map(Date::hashCode).orElse(0); // empty hash code -> 0
    }
}

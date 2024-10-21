package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Represents datetime of a wedding in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDatetime(String)}
 */
public class Datetime {

    public static final String MESSAGE_CONSTRAINTS = "Date should be valid and in the format of dd/MM/yyyy";
    public static final String VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$";
    public final String value;

    /**
     * Constructs an {@code Datetime}.
     *
     * @param datetime A valid datetime.
     */
    public Datetime(String datetime) {
        requireNonNull(datetime);
        checkArgument(isValidDatetime(datetime), MESSAGE_CONSTRAINTS);
        this.value = datetime;
    }

    /**
     * Returns true if a given string is a valid datetime.
     */
    public static boolean isValidDatetime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(test, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Datetime)) {
            return false;
        }

        Datetime otherDatetime = (Datetime) other;
        return value.equals(otherDatetime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

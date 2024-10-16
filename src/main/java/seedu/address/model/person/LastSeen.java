package seedu.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the date a person was last seen in the address book
 */
public class LastSeen {

    public static final String MESSAGE_CONSTRAINTS = "Date should be in the format DD-MM-YYYY";

    public LocalDate value;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public LastSeen(String lastSeen) {
        requireNonNull(lastSeen);
        checkArgument(isValidDate(lastSeen), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(lastSeen, formatter);
    }

    private static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return formatter.format(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LastSeen)) {
            return false;
        }

        LastSeen otherAddress = (LastSeen) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

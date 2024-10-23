package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Class for WeddingDate
 */
public class WeddingDate {
    public static final String MESSAGE_CONSTRAINTS = "Date is not in the correct format. Please use the format "
            + "DD/MM/YYYY.";
    public final LocalDate fullDate;

    /**
     * WeddingDate object constructor
     * @param date
     */
    public WeddingDate(LocalDate date) {
        requireNonNull(date);
        fullDate = date;
    }

    public static boolean isValidWeddingDate(String dateString) {
        requireNonNull(dateString);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WeddingDate)) {
            return false;
        }

        WeddingDate otherDate = (WeddingDate) other;
        return fullDate.equals(otherDate.fullDate);
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fullDate.format(formatter);
    }

}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Represents a Person's appointment date in the address book.
 * Guarantees: immutable; is always valid
 */
public class Appt {
    public static final String MESSAGE_CONSTRAINTS = null;

    /**
     * Comparator to compare two appointments by their date and time.
     * Used for sorting appointments by date and time.
     * 
     * @param appt1
     * @param appt2
     * @return int
     * @see Comparator
     */
    public static final Comparator<Appt> DATETIME_COMPARATOR = new Comparator<Appt>() {
        @Override
        public int compare(Appt appt1, Appt appt2) {
            return appt1.getDateTime().compareTo(appt2.getDateTime());
        }
    };

    private final LocalDateTime dateTime;

    /**
     * Constructs a {@code Appt}.
     * 
     * @param dateTime A valid appointment date.
     */
    public Appt(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appt // instanceof handles nulls
                        && dateTime.equals(((Appt) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    public static boolean isValidAppt(String trimmedDate) {
        return true;
    }

    @Override
    public String toString() {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

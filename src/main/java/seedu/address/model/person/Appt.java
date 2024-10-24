package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import seedu.address.model.healthservice.HealthService;

/**
 * Represents a Person's appointment date in the address book.
 * Guarantees: immutable; is always valid
 */
public class Appt {
    public static final String MESSAGE_CONSTRAINTS = null;

    /**
     * Comparator to compare two appointments by their date and time.
     * Used for sorting appointments by date and time.
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

    public boolean isOn(LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    public boolean isAfter(LocalDate date) {
        return dateTime.toLocalDate().isAfter(date);
    }

    /**
     * returns true if appointment date is the same as date or after date
     */
    public boolean isAfterOrOn(LocalDate date) {
        LocalDate curr = dateTime.toLocalDate();
        return isAfter(date) || isOn(date);
    }

    public boolean isBefore(LocalDate date) {
        return dateTime.toLocalDate().isBefore(date);
    }

    public boolean isBeforeOrOn(LocalDate date) {
        return isBefore(date) || isOn(date);
    }

    public boolean isBetweenDates(LocalDate start, LocalDate end) {
        return isAfterOrOn(start) && isBeforeOrOn(end);
    }

    public boolean isSameService(HealthService service) {
        return true;
    }

    /**
     * returns true if appointment's date is between start and end date and has same healthservice
     */
    public boolean isBetweenDatesAndMatchService(AppointmentDateFilter dateFilter) {
        LocalDate startDate = dateFilter.getStartDate() == null ? LocalDate.now() : dateFilter.getStartDate();
        LocalDate endDate = dateFilter.getEndDate();
        HealthService service = dateFilter.getHealthService();

        return isBetweenDates(startDate, endDate) && isSameService(service);
    }
}

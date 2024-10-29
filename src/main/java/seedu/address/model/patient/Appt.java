package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import seedu.address.model.appointmentdatefilter.AppointmentDateFilter;
import seedu.address.model.healthservice.HealthService;

/**
 * Represents a Patient's appointment date and health service in the address book.
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
    private final HealthService healthService;

    /**
     * Constructs a {@code Appt}.
     *
     * @param dateTime A valid appointment date.
     * @param healthService A valid health service.
     */
    public Appt(LocalDateTime dateTime, HealthService healthService) {
        requireNonNull(dateTime);
        requireNonNull(healthService);
        this.dateTime = dateTime;
        this.healthService = healthService;
    }

    /**
     * Returns the date and time of the appointment.
     * @return LocalDateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the health service of the appointment.
     */
    public HealthService getHealthService() {
        return healthService;
    }

    /**
     * Returns the name of the health service of the appointment.
     * @return String
     */
    public String getHealthServiceName() {
        return healthService.toString();
    }

    /**
     * Returns true if both appointments have the same date and time.
     * This defines a stronger notion of equality between two appointments.
     * @param other appointment
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appt // instanceof handles nulls
                        && dateTime.equals(((Appt) other).dateTime)); // state check
    }

    /**
     * Returns the hashcode of the appointment's date and time.
     */
    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    /**
     * Returns a string representation of the appointment
     * @param trimmedDate
     * @return
     */
    public static boolean isValidAppt(String trimmedDate) {
        return true;
    }

    /**
     * Returns true if appointment date is the same as date
     * @param date
     * @return
     */
    public boolean isOn(LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    /**
     * Returns true if appointment date is after date
     * @param date
     * @return
     */
    public boolean isAfter(LocalDate date) {
        return dateTime.toLocalDate().isAfter(date);
    }

    /**
     * returns true if appointment date is the same as date or after date
     */
    public boolean isAfterOrOn(LocalDate date) {
        return isAfter(date) || isOn(date);
    }

    /**
     * Returns true if appointment date is before date
     * @param date
     * @return
     */
    public boolean isBefore(LocalDate date) {
        return dateTime.toLocalDate().isBefore(date);
    }

    /**
     * Returns true if appointment date is the same as date or before date
     * @param date
     * @return
     */
    public boolean isBeforeOrOn(LocalDate date) {
        return isBefore(date) || isOn(date);
    }

    /**
     * Returns true if appointment date is between start and end date
     * @param start
     * @param end
     * @return
     */
    public boolean isBetweenDates(LocalDate start, LocalDate end) {
        return isAfterOrOn(start) && isBeforeOrOn(end);
    }

    /**
     * Returns true if appointment has the same health service
     * @param service
     * @return
     */
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

    /**
     * Returns a string representation of the appointment
     * in the form of a date and time.
     * @return String
     */
    @Override
    public String toString() {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " " + healthService;
    }
}

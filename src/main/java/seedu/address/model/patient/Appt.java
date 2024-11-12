package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Comparator;
import java.util.Objects;

import seedu.address.model.appointmentdatefilter.AppointmentDateFilter;
import seedu.address.model.healthservice.HealthService;

/**
 * Represents a Patient's appointment date and health service in the address book.
 * Guarantees: immutable; is always valid
 */
public class Appt {
    public static final String DATETIME_MESSAGE_CONSTRAINTS = "Invalid date and time. "
            + "Please enter a valid date and time.";

    /**
     * Comparator to compare two appointments by their date and time.
     * Used for sorting appointments by date and time.
     *
     * @param appt1
     * @param appt2
     * @return int
     * @see Comparator
     */
    public static final Comparator<Appt> DATETIME_COMPARATOR = (appt1, appt2) -> appt1.getDateTime()
            .compareTo(appt2.getDateTime());

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern("uuuu-MM-dd")
        .optionalStart()
        .appendLiteral(' ')
        .optionalEnd()
        .appendPattern("HH:mm")
        .toFormatter();
    public static final DateTimeFormatter STRICT_FORMATTER = FORMATTER.withResolverStyle(ResolverStyle.STRICT);

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
     * Returns true if both appointments have the same date and time.
     * This defines a stronger notion of equality between two appointments.
     * @param other appointment
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appt)) {
            return false;
        }

        Appt e = (Appt) other;
        return dateTime.isEqual(e.dateTime);
    }

    /**
     * Returns the hashcode of the appointment's date and time.
     */
    @Override
    public int hashCode() {
        return Objects.hash(dateTime, healthService);
    }

    /**
     * Returns true if the appointment date is valid.
     * @param trimmedDateTime
     * @return
     */
    public static boolean isValidDateTime(String trimmedDateTime) {
        try {
            LocalDateTime.parse(trimmedDateTime, STRICT_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        } catch (DateTimeException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
     * Returns true if appointment date and time is after dateTime
     * @param dateTime
     * @return
     */
    public boolean isAfterOrOn(LocalDateTime dateTime) {
        return this.dateTime.isAfter(dateTime) || this.dateTime.isEqual(dateTime);
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
        assert end != null;
        return isAfterOrOn(start) && isBeforeOrOn(end);
    }

    /**
     * Returns true if appointment has the same health service
     * @param service
     * @return
     */
    public boolean isSameService(HealthService service) {
        if (service == null) {
            return true;
        }
        return healthService.equals(service);
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
        return dateTime.format(DateTimeFormatter.ofPattern("d MMM uuuu, h:mma")) + "   " + healthService;
    }
}

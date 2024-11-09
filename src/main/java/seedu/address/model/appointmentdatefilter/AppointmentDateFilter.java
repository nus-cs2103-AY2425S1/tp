package seedu.address.model.appointmentdatefilter;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.model.healthservice.HealthService;

/**
 * Represents a AppointmentDate object used for filtering patients based on their appointment dates and healthservice;
 */
public class AppointmentDateFilter {

    public static final String ONE_DATE_MESSAGE_CONSTRAINTS = "Dates should follow the format YYYY-MM-DD";
    public static final String TWO_DATE_MESSAGE_CONSTRAINTS = ONE_DATE_MESSAGE_CONSTRAINTS
            + " and end date should be after start date";
    public static final String END_DATE_MESSAGE_CONSTRAINTS = "If start date is not specified, "
            + "end date should be after today's date: " + LocalDate.now();

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final HealthService healthService;

    /**
     * Constructs an {@code appointmentdatefilter}
     * @param startDate An optional startDate
     * @param endDate A valid endDate
     * @param healthService An optional HealthService
     */
    public AppointmentDateFilter(LocalDate startDate, LocalDate endDate, HealthService healthService) {
        requireNonNull(endDate);
        this.endDate = endDate;
        this.healthService = healthService;
        if (startDate != null) {
            checkArgument(isValidStartAndEndDate(startDate, endDate), TWO_DATE_MESSAGE_CONSTRAINTS);
            this.startDate = startDate;
        } else {
            this.startDate = LocalDate.now();
        }
    }

    /**
     * Parses the String date into a LocalDate object
     * @param date
     * @return LocalDate from parsed String date
     * @throws DateTimeParseException
     */
    public static LocalDate parseDate(String date) throws DateTimeParseException {
        requireNonNull(date);
        DateTimeFormatter strictFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(date, strictFormatter);
    }

    /**
     * Returns if a given string is a valid date
     */
    public static boolean isValidDate(String date) {
        try {
            parseDate(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * returns true if start and end dates are valid and end date is after start date
     */
    public static boolean isValidStartAndEndDate(LocalDate startDate, LocalDate endDate) {
        return !endDate.isBefore(startDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public HealthService getHealthService() {
        return healthService;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentDateFilter)) {
            return false;
        }

        AppointmentDateFilter otherFilter = (AppointmentDateFilter) other;
        if (healthService == null && otherFilter.healthService == null) {
            return endDate.isEqual(otherFilter.endDate) && startDate.isEqual(otherFilter.startDate);
        } else if ((healthService != null && otherFilter.healthService == null)
                || (healthService == null && otherFilter.healthService != null)) {
            return false;
        }
        return startDate.isEqual(otherFilter.startDate) && endDate.isEqual(otherFilter.endDate)
                && healthService.equals(otherFilter.healthService);
    }

    @Override
    public String toString() {
        String healthService = this.healthService == null ? "" : " with service " + this.healthService;
        return "within range " + startDate + " to " + endDate + healthService;
    }

}

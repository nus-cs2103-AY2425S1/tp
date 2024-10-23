package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.model.healthservice.HealthService;

/**
 * Represents a AppointmentDate object used for filtering patients based on their appointment dates and healthservice;
 */
public class AppointmentDateFilter {

    public static final String ONE_DATE_MESSAGE_CONSTRAINTS = "Dates should follow the format YYYY-MM-DD";
    public static final String TWO_DATE_MESSAGE_CONSTRAINTS = ONE_DATE_MESSAGE_CONSTRAINTS + "\n"
            + "end date should be after start date";

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final HealthService healthService;

    /**
     * Constructs an {@code AppointmentDateFilter}
     * @param startDate An optional startDate
     * @param endDate A valid endDate
     * @param healthService An optional HealthService
     */
    public AppointmentDateFilter(LocalDate startDate, LocalDate endDate, HealthService healthService) {
        requireNonNull(endDate);
        this.endDate = endDate;
        if (startDate != null) {
            checkArgument(isValidStartAndEndDate(startDate, endDate), TWO_DATE_MESSAGE_CONSTRAINTS);
            this.startDate = startDate;
            this.healthService = healthService;
        } else {
            this.startDate = null;
            this.healthService = healthService;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
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
        return endDate.isAfter(startDate);
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

}

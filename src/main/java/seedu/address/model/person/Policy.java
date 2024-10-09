package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Policy {

    public static final String MESSAGE_CONSTRAINTS = "Policy details should be in the format 'policyName startDate endDate', "
            + "where dates are in 'yyyy-MM-dd' format.";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String policyName;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Policy(String policyName, String startDateStr, String endDateStr) {
        checkArgument(isValidPolicy(policyName, startDateStr, endDateStr), MESSAGE_CONSTRAINTS);

        this.policyName = policyName.trim();
        this.startDate = parseDate(startDateStr);
        this.endDate = parseDate(endDateStr);
    }

    /**
     * Parses a date string into a {@code LocalDate}.
     *
     * @param dateStr The date string to parse.
     * @return A {@code LocalDate} object representing the date.
     */
    private static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static boolean isValidPolicy(String policyName, String startDateStr, String endDateStr) {
        requireNonNull(policyName);
        requireNonNull(startDateStr);
        requireNonNull(endDateStr);

        // Check policyName is non-empty
        if (policyName.trim().isEmpty()) {
            return false;
        }

        // Check dates are valid and endDate is not before startDate
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
            LocalDate endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);

            return !endDate.isBefore(startDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return policyName.hashCode() + startDate.hashCode() + endDate.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Policy
                && policyName.equals(((Policy) other).policyName)
                && startDate.equals(((Policy) other).startDate)
                && endDate.equals(((Policy) other).endDate));
    }

    @Override
    public String toString() {
        return policyName + " (" + startDate + " to " + endDate + ")";
    }
}

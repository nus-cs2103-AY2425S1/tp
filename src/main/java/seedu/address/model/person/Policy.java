package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

/**
 * Represents an insurance policy in the address book.
 */
public class Policy {
    public static final String MESSAGE_CONSTRAINTS = "Policy details should be in the format 'policyName startDate "
            + "endDate', where dates are in 'yyyy-MM-dd' format.";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String policyName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Payment payment;

    /**
     * Constructs a {@code Policy}.
     *
     * @param policyName Name of the policy.
     * @param startDateStr Start date of the policy.
     * @param endDateStr End date of the policy.
     */
    public Policy(String policyName, String startDateStr, String endDateStr, String insurancePayment) {
        checkArgument(isValidPolicy(policyName, startDateStr, endDateStr, insurancePayment), MESSAGE_CONSTRAINTS);
        this.policyName = policyName.trim();
        this.startDate = parseDate(startDateStr);
        this.endDate = parseDate(endDateStr);
        this.payment = new Payment(insurancePayment);
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

    /**
     * Returns true if the given policy details are valid.
     */
    public static boolean isValidPolicy(String policyName, String startDateStr, String endDateStr,
                                        String payment) {
        requireNonNull(policyName);
        requireNonNull(startDateStr);
        requireNonNull(endDateStr);
        requireNonNull(payment);

        // Check policyName is non-empty
        if (policyName.trim().isEmpty()) {
            return false;
        }

        // Check dates are valid and endDate is not before startDate
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
            LocalDate endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);

            if (endDate.isBefore(startDate) || !Payment.isValidInsurancePayment(payment)) {
                return false;
            }
            return true;

        } catch (DateTimeParseException e) {
            return false;
        }

    }

    public String getPolicyName() {
        return policyName;
    }

    public LocalDate getPolicyPaymentDueDate() {
        return payment.getPaymentDueDate();
    }

    public static Comparator<Policy> getPolicyPaymentDueDateComparator() {
        return Comparator.comparing(policy -> policy.getPolicyPaymentDueDate());
    }

    @Override
    public int hashCode() {
        return policyName.hashCode() + startDate.hashCode()
                + endDate.hashCode() + payment.hashCode();
    }


    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Policy
                && policyName.equals(((Policy) other).policyName)
                && startDate.equals(((Policy) other).startDate)
                && endDate.equals(((Policy) other).endDate)
                && payment.equals(((Policy) other).payment));
    }

    @Override
    public String toString() {
        return policyName + " (" + startDate + " to " + endDate + ")\nInsurance Payment: " + payment.toString();
    }
}

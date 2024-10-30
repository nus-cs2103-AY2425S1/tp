package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an insurance policy in the address book.
 */
public class Policy {
    public static final String MESSAGE_CONSTRAINTS = "Policy details should be in the format 'policyName startDate "
            + "endDate' paydate amountDue, where dates are in 'yyyy-MM-dd' format.";

    //private static final String FULLY_PAID = "Fully Paid";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String TOSTRINGFORMATTER = "([\\w\\s]+)\\s+\\((\\d{4}-\\d{2}-\\d{2})\\s*"
            + "to\\s*(\\d{4}-\\d{2}-\\d{2})\\)\\s*"
           + "(?:\\$([0-9.]+)\\s*due\\s*on\\s*(\\d{4}-\\d{2}-\\d{2})|(?<paid>Fully Paid))";

    private static final String POLICY_NAME = "policyName"; // Policy name
    private static final String START_DATE = "startDate"; // Start date of the policy
    private static final String END_DATE = "endDate"; // End date of the policy
    private static final String PAYMENT_DATE = "paymentDate"; // Date of the payment
    private static final String PAYMENT_AMOUNT = "paymentAmount"; // Amount of the payment

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
     * Constructs a Policy object with the given policy description.
     * The policy description must adhere to a valid format and include the policy name, start date, end date,
     * payment amount, and payment date. It extracts and assigns these details to the respective fields.
     *
     * @param policyDescription A string describing the policy details, including the name, start date, end date,
     *                          payment amount, and payment date.
     */
    public Policy(String policyDescription) {
        checkArgument(isValidPolicy(policyDescription), MESSAGE_CONSTRAINTS);
        Map<String, String> policyDetails = extractPolicyDetails(policyDescription);

        this.policyName = policyDetails.get(POLICY_NAME);
        this.startDate = parseDate(policyDetails.get(START_DATE));
        this.endDate = parseDate(policyDetails.get(END_DATE));
        String paymentAmount = policyDetails.get(PAYMENT_AMOUNT);
        String paymentDate = policyDetails.get(PAYMENT_DATE);

        this.payment = new Payment(paymentDate + " " + paymentAmount);
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

    /**
     * Returns true if the given policy string is valid.
     */
    public static boolean isValidPolicy(String policyDescription) {
        Map<String, String> policyDetails = extractPolicyDetails(policyDescription);
        if (policyDetails == null) {
            return false;
        }
        String policyName = policyDetails.get(POLICY_NAME);
        String startDate = policyDetails.get(START_DATE);
        String endDate = policyDetails.get(END_DATE);
        String paymentAmount = policyDetails.get(PAYMENT_AMOUNT);
        String paymentDate = policyDetails.get(PAYMENT_DATE);
        return isValidPolicy(policyName, startDate, endDate, paymentDate + " " + paymentAmount);
    }

    public String getPolicyName() {
        return policyName;
    }

    public void updateNextPaymentDate() {
        payment.updatePaymentDueDate(this);
    }

    public LocalDate getPolicyPaymentDueDate() {
        return payment.getPaymentDueDate();
    }

    public boolean isExpiringSoon() {
        return payment.getPaymentDueDate().plusYears(1).isAfter(endDate);
    }

    public static Comparator<Policy> getPolicyPaymentDueDateComparator() {
        return Comparator.comparing(policy -> policy.getPolicyPaymentDueDate());
    }

    /**
     * Extracts policy details from the provided policy description string using a regular expression pattern.
     * The string must match the format defined by the {@code TOSTRINGFORMATTER} pattern, and the details
     * will be extracted in the following order: policy name, start date, end date, payment amount, and payment date.
     *
     * @param policyDescription The string describing the policy details,
     *                          expected to match the format specified by the regex pattern.
     * @return A list of strings containing the extracted policy details
     *         or {@code null} if no match is found in the policy description.
     */
    public static Map<String, String> extractPolicyDetails(String policyDescription) {
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(TOSTRINGFORMATTER);
        Matcher matcher = pattern.matcher(policyDescription);

        // Create a HashMap to store the extracted details
        Map<String, String> policyDetails = new HashMap<>();

        // If a match is found, extract and add details to the HashMap
        if (matcher.find()) {
            policyDetails.put(POLICY_NAME, matcher.group(1));
            policyDetails.put(START_DATE, matcher.group(2));
            policyDetails.put(END_DATE, matcher.group(3));
            if (matcher.group(4) != null && matcher.group(5) != null) {
                // Handle the case when there is an amount due
                policyDetails.put(PAYMENT_AMOUNT, matcher.group(4));
                policyDetails.put(PAYMENT_DATE, matcher.group(5));
            } else if (matcher.group("paid") != null) {
                policyDetails.put(PAYMENT_AMOUNT, LocalDate.MAX.toString());
                policyDetails.put(PAYMENT_DATE, BigDecimal.ZERO.toString());
            }

            return policyDetails;
        }
        return null;
    }
    public boolean isSamePolicy(Policy policy) {
        return policyName.equals(policy.getPolicyName());
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
        return policyName + " (" + startDate + " to " + endDate + ") " + payment.toString();
    }

}

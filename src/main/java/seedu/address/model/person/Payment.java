package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's insurance payment in the address book.
 */
public class Payment {
    public static final String MESSAGE_CONSTRAINTS = "Insurance payments should be in the format 'yyyy-MM-dd' "
            + "\nAmount is a positive number with up to two decimal places!";

    /*
     * The insurance payment should be in the format 'yyyy-MM-dd amount'.
     * Example: 2023-11-01 300.00
     */
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d+(\\.\\d{1,2})?";



    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String FULLY_PAID_PAYMENT = BigDecimal.ZERO.toString() + " " + LocalDate.MAX.toString();

    public final String value;
    private LocalDate paymentDueDate;
    private BigDecimal amount;

    /**
     * Constructs an {@code InsurancePayment}.
     *
     * @param insurancePayment A valid insurance payment string.
     */
    public Payment(String insurancePayment) {
        requireNonNull(insurancePayment);
        checkArgument(isValidInsurancePayment(insurancePayment), MESSAGE_CONSTRAINTS);
        value = insurancePayment;
        if (value.equals(FULLY_PAID_PAYMENT)) {
            paymentDueDate = LocalDate.MAX;
            amount = BigDecimal.ZERO;
        } else {
            String[] parts = insurancePayment.trim().split("\\s+");
            paymentDueDate = LocalDate.parse(parts[0], DATE_FORMATTER);
            amount = new BigDecimal(parts[1]);
        }
    }

    /**
     * Returns true if a given string is a valid insurance payment.
     */
    public static boolean isValidInsurancePayment(String test) {
        requireNonNull(test);
        if (test.equals(FULLY_PAID_PAYMENT)) {
            return true;
        }
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        String[] parts = test.trim().split("\\s+");
        if (parts.length != 2) {
            return false;
        }
        try {
            LocalDate.parse(parts[0], DATE_FORMATTER);
            BigDecimal amount = new BigDecimal(parts[1]);
            return amount.compareTo(BigDecimal.ZERO) > 0;
        } catch (DateTimeParseException | NumberFormatException e) {
            return false;
        }
    }

    /**
     * Updates the payment due date of the insurance payment.
     * @param policy
     */
    public void updatePaymentDueDate(Policy policy) {
        if (policy.isExpiringSoon()) {
            paymentDueDate = LocalDate.MAX;
            amount = BigDecimal.ZERO;
        } else {
            paymentDueDate = paymentDueDate.plusYears(1);
        }
    }

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    @Override
    public String toString() {
        if (amount.equals(BigDecimal.ZERO) || paymentDueDate.equals(LocalDate.MAX)) {
            return "Fully Paid";
        }
        return String.format("$%.2f due on %s", amount, paymentDueDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Payment && value.equals(((Payment) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

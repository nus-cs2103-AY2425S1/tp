package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_AMOUNT_OUT_OF_RANGE;

import java.time.LocalDate;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Client's transaction in the address book.
 */
public class Transaction {

    public static final String MESSAGE_CONSTRAINTS = "Amount should have at most 2 decimal places.\n"
            + "It should contain only digits (0-9), one optional decimal point (.) and one optional minus sign (-) "
            + "with no other symbols.\n"
            + "There should be at least 1 digit before and 1 digit after the decimal point.";

    public static final String VALIDATION_REGEX = "^-?\\d+(,\\d{3})*(\\.\\d{1,2})?$";
    private final String description;
    private final double amount;
    private final String otherParty;
    private final LocalDate date;

    /**
     * Every field must be present and not null.
     */
    public Transaction(String description, double amount, String otherParty, LocalDate date) {
        requireAllNonNull(description, amount, otherParty, date);
        this.description = description;
        this.amount = amount;
        this.otherParty = otherParty;
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Throws a {@code ParseException} if the amount is out of range.
     */
    public static void verifyAmountIsWithinRange(double amount) throws ParseException {
        double minAmount = -Math.pow(10, 9);
        double maxAmount = Math.pow(10, 9);

        if (amount < minAmount || amount > maxAmount) {
            throw new ParseException(MESSAGE_AMOUNT_OUT_OF_RANGE);
        }

    }

    public String getDescription() {
        return description;
    }
    public double getAmount() {
        return amount;
    }

    public String getOtherParty() {
        return otherParty;
    }
    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return description.equals(otherTransaction.getDescription())
                && (amount == otherTransaction.getAmount())
                && otherParty.equals(otherTransaction.getOtherParty())
                && date.equals(otherTransaction.getDate());
    }

    @Override
    public String toString() {
        return Messages.format(this);
    }
}

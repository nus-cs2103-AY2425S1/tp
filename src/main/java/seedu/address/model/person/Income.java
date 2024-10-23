package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's income level in SGD per annum in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIncome(String)}
 */
public class Income {

    public static final String MESSAGE_CONSTRAINTS = "Income levels cannot be negative or have a non-numerical "
            + "character.";

    public final int value;

    /**
     * Constructs an {@code Income}.
     *
     * @param value A valid income amount.
     */
    public Income(int value) {
        requireNonNull(value);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid income amount.
     * It does so by checking whether (1) the string is a valid integer and (2) if the integer value is non-negative
     */
    public static boolean isValidIncome(String incomeStringToBeTested) {
        try {
            int income = Integer.parseInt(incomeStringToBeTested);
            return income >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("$%d per annum", value);
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == this) {
            return true;
        } else if (!(otherObj instanceof Income)) {
            return false;
        }
        return this.value == ((Income) otherObj).value;
    }

}

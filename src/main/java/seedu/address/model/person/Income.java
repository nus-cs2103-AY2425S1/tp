package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Income group in the address book.
 * Guarantees: immutable; is an Enum as declared in {@link Income#IncomeGroup}
 */
public class Income implements OptionalField {

    public static final String MESSAGE_CONSTRAINTS = "Income group should be one of types: <none/low/mid/high>";

    public enum IncomeGroup {
        NONE, // Represents unemployed, not empty value
        LOW,
        MEDIUM,
        HIGH
    }

    public final IncomeGroup value;

    /**
     * Constructs an {@code Income}.
     *
     * @param incomeGroup A valid IncomeGroup enum.
     */
    public Income(IncomeGroup incomeGroup) {
        requireNonNull(incomeGroup);

        value = incomeGroup;
    }

    /**
     * Constructs an {@code Income}.
     *
     * @param incomeGroup A valid IncomeGroup enum, in String format.
     */
    public Income(String incomeGroup) {
        requireNonNull(incomeGroup);

        value = getIncomeGroupFromString(incomeGroup);
    }

    /**
     * Constructs an empty {@code IncomeGroup}.
     * Package-private.
     */
    Income() {
        value = null;
    }

    /**
     * Returns an empty {@code IncomeGroup}.
     */
    public static Income createEmpty() {
        return EmptyIncome.get();
    }

    /**
     * Returns false as this field is not empty.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Converts a value into an IncomeGroup enum.
     *
     * @param value the value to convert.
     * @return the IncomeGroup enum corresponding to the value.
     */
    public static IncomeGroup getIncomeGroupFromString(String value) {
        switch (value) {
        case "none":
            return IncomeGroup.NONE;
        case "low":
            return IncomeGroup.LOW;
        case "mid":
            return IncomeGroup.MEDIUM;
        case "high":
            return IncomeGroup.HIGH;
        }

        return null;
    }

    /**
     * Returns if a given string is a valid income.
     */
    public static boolean isValidIncome(String test) {
        IncomeGroup converted = getIncomeGroupFromString(test);

        return converted != null;
    }

    @Override
    public String toString() {
        switch (value) {
        case NONE:
            return "none";
        case LOW:
            return "low";
        case MEDIUM:
            return "mid";
        case HIGH:
            return "high";
        }

        throw new RuntimeException("Invalid IncomeGroup enum type");
    }

    /**
     * Returns the String to be presented on the UI.
     */
    @Override
    public String getValueForUI() {
        if (value == IncomeGroup.NONE) {
            return "No income";
        } else {
            return toString().substring(0, 1).toUpperCase() + toString().substring(1) + " income";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Income)) {
            return false;
        }

        Income otherIncome = (Income) other;
        return value.equals(otherIncome.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

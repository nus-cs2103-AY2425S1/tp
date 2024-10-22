package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a how often a client's policy is updated in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFrequency(String)}
 */
public class Frequency {

    enum Months {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Frequency can only be 1, 3, 6 or 12 months";

    public final String value;
    public final Months[] months;

    /**
     * Constructs a {@code Frequency}.
     * Hard coded months for each frequency.
     * @param frequency
     * @throws IllegalArgumentException
     */
    public Frequency(String frequency) throws IllegalArgumentException {
        requireNonNull(frequency);
        checkArgument(isValidFrequency(frequency), MESSAGE_CONSTRAINTS);
        this.value = frequency;
        switch (frequency) {
        case "0":
            this.months = new Months[]{};
            break;
        case "1":
            this.months = new Months[]{Months.JANUARY, Months.FEBRUARY, Months.MARCH, Months.APRIL, Months.MAY,
                Months.JUNE, Months.JULY, Months.AUGUST, Months.SEPTEMBER,
                Months.OCTOBER, Months.NOVEMBER, Months.DECEMBER};
            break;
        case "3":
            this.months = new Months[]{Months.JANUARY, Months.APRIL, Months.JULY, Months.OCTOBER};
            break;
        case "6":
            this.months = new Months[]{Months.JANUARY, Months.JULY};
            break;
        case "12":
            this.months = new Months[]{Months.JANUARY};
            break;
        default:
            throw new IllegalArgumentException("Invalid frequency");
        }
    }
    /**
     * Returns true if a given string is a valid frequency.
     * @param test input integer frequency
     * @return true if the frequency is valid (ie 1, 3, 6, 12)
     */
    public static boolean isValidFrequency(String test) {
        return test.equals("0") || test.equals("1") || test.equals("3") || test.equals("6") || test.equals("12");
    }
    @Override
    public String toString() {
        return this.value;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Frequency)) {
            return false;
        }

        Frequency otherFrequency = (Frequency) other;
        return value.equals(otherFrequency.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

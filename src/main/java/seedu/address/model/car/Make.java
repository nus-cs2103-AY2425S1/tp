package seedu.address.model.car;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Car's Make in MATER.
 * Guarantees: immutable; is valid as declared in {@link #isValidMake(String)}
 */
public class Make {

    public static final String MESSAGE_CONSTRAINTS =
            "Car make should only contain alphanumeric characters,"
                    + "with the first character being a captial letter"
                    + "and it should not contain spaces nor be blank.";
    public static final String VALIDATION_REGEX = "[A-Z][\\p{Alnum}]*";

    public final String make;

    /**
     * Constructs a {@code Make}.
     *
     * @param make A valid make.
     */
    public Make(String make) {
        requireNonNull(make);
        checkArgument(isValidMake(make), MESSAGE_CONSTRAINTS);
        this.make = make;
    }

    /**
     * Returns true if a given string is a valid Make.
     */
    public static boolean isValidMake(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.make;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Make)) {
            return false;
        }

        Make otherName = (Make) other;
        return this.make.equals(otherName.make);
    }

    @Override
    public int hashCode() {
        return make.hashCode();
    }

}

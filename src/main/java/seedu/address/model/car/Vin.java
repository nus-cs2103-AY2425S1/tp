package seedu.address.model.car;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Car's Vehicle Identification Number (Vin) in MATER.
 * Guarantees: immutable; is valid as declared in {@link #isValidVin(String)}
 */
public class Vin {

    public static final String MESSAGE_CONSTRAINTS = "Vehicle Identification Numbers should only contain"
            + "alphanumeric characters (length 17). Characters should be capitalised and should not contain"
            + "spaces or be blank.";
    public static final String VALIDATION_REGEX = "^[A-Z0-9]{17}$";

    public final String vin;

    /**
     * Constructs an {@code Vrn}.
     *
     * @param vin A valid vin.
     */
    public Vin(String vin) {
        requireNonNull(vin);
        checkArgument(isValidVin(vin), MESSAGE_CONSTRAINTS);
        this.vin = vin;
    }

    /**
     * Returns if a given string is a valid Vin.
     */
    public static boolean isValidVin(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.vin;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Vin)) {
            return false;
        }

        Vin otherVin = (Vin) other;
        return this.vin.equals(otherVin.vin);
    }

    @Override
    public int hashCode() {
        return this.vin.hashCode();
    }
}

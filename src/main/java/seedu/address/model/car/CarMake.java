package seedu.address.model.car;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a CarMake in MATER.
 * Guarantees: immutable; is valid as declared in {@link #isValidCarMake(String)}
 */
public class CarMake {

    public static final String MESSAGE_CONSTRAINTS = "Car make should only contain alphanumeric characters, "
            + "with the first character being a capital letter, and it should not contain spaces nor be blank.";
    public static final String VALIDATION_REGEX = "[A-Z][\\p{Alnum}]*";

    public final String carMake;

    /**
     * Constructs a {@code CarMake}.
     *
     * @param carMake A valid carMake.
     */
    public CarMake(String carMake) {
        requireNonNull(carMake);
        checkArgument(isValidCarMake(carMake), MESSAGE_CONSTRAINTS);
        this.carMake = carMake;
    }

    /**
     * Returns true if a given string is a valid CarMake.
     */
    public static boolean isValidCarMake(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.carMake;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CarMake)) {
            return false;
        }

        CarMake otherCarMake = (CarMake) other;
        return this.carMake.equals(otherCarMake.carMake);
    }

    @Override
    public int hashCode() {
        return carMake.hashCode();
    }

}

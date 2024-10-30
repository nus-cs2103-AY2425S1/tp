package seedu.address.model.car;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a CarModel in MATER.
 * Guarantees: immutable; is valid as declared in {@link #isValidCarModel(String)}
 */
public class CarModel {

    public static final String MESSAGE_CONSTRAINTS = "Car model should only contain alphanumeric characters, "
            + "with the first character being a capital letter or number, "
            + "and it should not be blank or exceed 40 characters.";
    public static final String VALIDATION_REGEX = "[A-Z0-9][\\p{Alnum} .-]{0,39}";

    public final String carModel;

    /**
     * Constructs a {@code CarModel}.
     *
     * @param carModel A valid carModel.
     */
    public CarModel(String carModel) {
        requireNonNull(carModel);
        checkArgument(isValidCarModel(carModel), MESSAGE_CONSTRAINTS);
        this.carModel = carModel;
    }

    /**
     * Returns true if a given string is a valid CarModel.
     */
    public static boolean isValidCarModel(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.carModel;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CarModel)) {
            return false;
        }

        CarModel otherCarModel = (CarModel) other;
        return this.carModel.equals(otherCarModel.carModel);
    }

    @Override
    public int hashCode() {
        return carModel.hashCode();
    }
}


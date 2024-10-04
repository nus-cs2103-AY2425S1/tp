package seedu.address.model.car;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Car's Model in MATER.
 * Guarantees: immutable; is valid as declared in {@link #isValidModel(String)}
 */
public class Model {

    public static final String MESSAGE_CONSTRAINTS =
            "Car model should only contain alphanumeric characters,"
                    + "with the first character being a captial letter or number"
                    + "and it should not be blank.";
    public static final String VALIDATION_REGEX = "[A-Z0-9][\\p{Alnum} .-]*";

    public final String model;

    /**
     * Constructs a {@code Model}.
     *
     * @param model A valid model.
     */
    public Model(String model) {
        requireNonNull(model);
        checkArgument(isValidModel(model), MESSAGE_CONSTRAINTS);
        this.model = model;
    }

    /**
     * Returns true if a given string is a valid Model.
     */
    public static boolean isValidModel(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.model;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Model)) {
            return false;
        }

        Model otherName = (Model) other;
        return this.model.equals(otherName.model);
    }

    @Override
    public int hashCode() {
        return model.hashCode();
    }
}


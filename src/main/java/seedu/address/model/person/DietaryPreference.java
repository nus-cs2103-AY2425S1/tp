package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's dietary preference in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDietaryPreference(String)}
 */
public class DietaryPreference {

    public static final String MESSAGE_CONSTRAINTS =
            "Dietary preference should be between 1 and 50 characters, "
                    + "and should only contain letters, spaces, hyphens, or commas.";

    /**
     * The dietary preference must only contain alphanumeric characters, spaces,
     * hyphens, or commas.
     * The preference should be between 1 and 50 characters long.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z\\s,-]{1,50}$";

    public final String value;

    /**
     * Constructs a {@code DietaryPreference}.
     *
     * @param dietaryPreference A valid dietary preference.
     */
    public DietaryPreference(String dietaryPreference) {
        requireNonNull(dietaryPreference);
        checkArgument(isValidDietaryPreference(dietaryPreference), MESSAGE_CONSTRAINTS);
        value = dietaryPreference;
    }

    public DietaryPreference() {
        value = "";
    }

    /**
     * Returns true if a given string is a valid dietary preference.
     */
    public static boolean isValidDietaryPreference(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DietaryPreference)) {
            return false;
        }

        DietaryPreference otherPreference = (DietaryPreference) other;
        return value.equals(otherPreference.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

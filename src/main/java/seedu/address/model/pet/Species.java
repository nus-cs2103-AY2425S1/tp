package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pet's species in PawPatrol.
 * Guarantees: immutable; is valid as declared in {@link #isValidSpecies(String)}
 */
public class Species {

    public static final String MESSAGE_CONSTRAINTS =
            "Species should only contain alphabetic characters, should be a single word, and it should not be blank";
    public static final String VALIDATION_REGEX = "[a-zA-Z]+";

    public final String value;

    /**
     * Constructs a {@code Species}.
     *
     * @param species A valid species.
     */
    public Species(String species) {
        requireNonNull(species);
        checkArgument(isValidSpecies(species), MESSAGE_CONSTRAINTS);
        value = species;
    }

    /**
     * Returns true if a given string is a valid species.
     */
    public static boolean isValidSpecies(String test) {
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

        // instanceof handles nulls
        if (!(other instanceof Species)) {
            return false;
        }

        Species otherSpecies = (Species) other;
        return value.equals(otherSpecies.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

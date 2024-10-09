package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pet's breed in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBreed(String)}
 */
public class Breed {

    public static final String MESSAGE_CONSTRAINTS =
            "Breeds should only contain alphanumeric characters, spaces, or hyphens, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} \\-]*";

    public final String breed;

    /**
     * Constructs a {@code Breed}.
     *
     * @param breed A valid breed.
     */
    public Breed(String breed) {
        requireNonNull(breed);
        checkArgument(isValidBreed(breed), MESSAGE_CONSTRAINTS);
        this.breed = breed;
    }

    /**
     * Returns true if a given string is a valid breed.
     */
    public static boolean isValidBreed(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return breed;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Breed)) {
            return false;
        }

        Breed otherBreed = (Breed) other;
        return breed.equals(otherBreed.breed);
    }

    @Override
    public int hashCode() {
        return breed.hashCode();
    }

}

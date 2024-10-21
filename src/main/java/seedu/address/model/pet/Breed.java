package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a Pet's breed in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBreed(String)}
 */
public class Breed {

    public static final String MESSAGE_CONSTRAINTS =
            "Breeds should only contain alphabetic characters, spaces, or hyphens, and it should not be blank";
    public static final String VALIDATION_REGEX = "[a-zA-Z][a-zA-Z \\-]*";

    public final String value;

    /**
     * Constructs a {@code Breed}.
     *
     * @param breed A valid breed.
     */
    public Breed(String breed) {
        requireNonNull(breed);
        checkArgument(isValidBreed(breed), MESSAGE_CONSTRAINTS);
        value = breed;
    }

    /**
     * Returns true if a given string is a valid breed.
     */
    public static boolean isValidBreed(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Retrieves the initials of the pet's breed as a String.
     */
    public String getInitials() {
        return Arrays.stream(value.split(" "))
                .map(word -> String.valueOf(word.charAt(0)))
                .collect(Collectors.joining());
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
        if (!(other instanceof Breed)) {
            return false;
        }

        Breed otherBreed = (Breed) other;
        return value.equals(otherBreed.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

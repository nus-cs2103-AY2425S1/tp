package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's family size (inclusive of himself) in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFamilySize(int)}
 */
public class FamilySize {

    public static final String MESSAGE_CONSTRAINTS =
            "Family size should be a positive integer";

    private final int value;

    /**
     * Constructs a {@code FamilySize}.
     *
     * @param size A valid size integer
     */
    public FamilySize(int size) {
        checkArgument(isValidFamilySize(size), MESSAGE_CONSTRAINTS);
        this.value = size;
    }

    /**
     * Returns true if a given integer is a valid family size, i.e. is positive.
     *
     * @param test An integer to be tested
     * @return {@code true} if the integer is valid, otherwise false
     */
    public static boolean isValidFamilySize(int test) {
        return test > 0;
    }

    /**
     * Returns value of {@code FamilySize}.
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FamilySize otherFamilySize)) {
            return false;
        }

        return this.value == otherFamilySize.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}

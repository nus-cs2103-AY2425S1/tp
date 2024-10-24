package seedu.address.model.wedding;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Class for WeddingName
 */
public class WeddingName {
    public static final int WEDDING_NAME_MAX_LEN = 30;

    public static final String MESSAGE_CONSTRAINTS =
            "Wedding names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String MESSAGE_LENGTH_LIMIT =
            "Wedding Name too long! Wedding names must be kept within " + WEDDING_NAME_MAX_LEN + " characters.";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public WeddingName(String name) {
        requireNonNull(name);
        checkArgument(isValidWeddingName(name), MESSAGE_CONSTRAINTS);
        checkArgument(isWithinCharLimit(name), MESSAGE_LENGTH_LIMIT);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidWeddingName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given name is within the character limit.
     */
    public static boolean isWithinCharLimit(String test) {
        return test.length() <= WEDDING_NAME_MAX_LEN;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WeddingName)) {
            return false;
        }

        WeddingName otherName = (WeddingName) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

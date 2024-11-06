package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wedding's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeddingName(String)}
 */
public class WeddingName {

    public static final String MESSAGE_CONSTRAINTS = "Wedding Name should be 2 person names separated with &.\n"
            + "It should only contain alphanumeric characters and spaces, and it should not be blank.\n"
            + "Example: Charlie & Hannah";
    public static final String VALIDATION_REGEX =
            "^\\s*([\\p{Alnum}]+(?:\\s+[\\p{Alnum}]+)*(?:\\s+(?:s/o|d/o|S/O|D/O)\\s+[\\p{Alnum}]"
                    + "+(?:\\s+[\\p{Alnum}]+)?)?)\\s*&\\s*([\\p{Alnum}]"
                    + "+(?:\\s+[\\p{Alnum}]+)*(?:\\s+(?:s/o|d/o|S/O|D/O)\\s"
                    + "+[\\p{Alnum}]+(?:\\s+[\\p{Alnum}]+)?)?)\\s*$";
    public final String value;
    /**
     * Constructs an {@code WeddingName}.
     *
     * @param weddingName A valid weddingName.
     */
    public WeddingName(String weddingName) {
        requireNonNull(weddingName);
        checkArgument(isValidWeddingName(weddingName), MESSAGE_CONSTRAINTS);
        this.value = weddingName;
    }

    /**
     * Returns true if a given string is a valid weddingName.
     */
    public static boolean isValidWeddingName(String test) {
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
        if (!(other instanceof WeddingName)) {
            return false;
        }

        WeddingName otherWeddingName = (WeddingName) other;
        return value.equals(otherWeddingName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

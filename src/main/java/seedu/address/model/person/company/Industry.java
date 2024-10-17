package seedu.address.model.person.company;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company's industry in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIndustry(String)}
 */
public class Industry {

    public static final String MESSAGE_CONSTRAINTS =
            "Industries should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Industry}.
     *
     * @param industry A valid industry.
     */
    public Industry(String industry) {
        requireNonNull(industry);
        checkArgument(isValidIndustry(industry), MESSAGE_CONSTRAINTS);
        value = industry;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidIndustry(String test) {
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
        if (!(other instanceof Industry)) {
            return false;
        }

        Industry otherIndustry = (Industry) other;
        return value.equals(otherIndustry.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

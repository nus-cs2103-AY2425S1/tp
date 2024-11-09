package spleetwaise.address.model.person;

import static java.util.Objects.requireNonNull;

import spleetwaise.commons.util.AppUtil;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidName(String)}
 *
 * Note: Names are currently case-sensitive, meaning "John" and "john" will be considered different names. This behavior
 * is due to the use of case-sensitive equality checks in the {@link #equals(Object)} method. In a future enhancement,
 * we plan to make names case-insensitive to match real-world scenarios where capitalization is not considered
 * significant for names.
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and certain special characters "
                    + "(.'&()\"/-) that are allowed as legal names or for ease of record and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z0-9.'&(),\\-][A-Za-z0-9.'&(),\"\\-/ ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        AppUtil.checkArgument(isValidName(trimmedName), MESSAGE_CONSTRAINTS);
        fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.trim().matches(VALIDATION_REGEX);
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
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

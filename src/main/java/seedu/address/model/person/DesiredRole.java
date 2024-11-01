package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a candidate's desired role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDesiredRole(String)}
 */
public class DesiredRole {

    public static final String MESSAGE_CONSTRAINTS =
        "Desired Role can take any value but should not be blank";

    /*
     * The first character of the desired role must not be a whitespace.
     */
    public static final String VALIDATION_REGEX = "^[A-Za-z][A-Za-z0-9 /]*$";

    public final String value;

    /**
     * Constructs a {@code DesiredRole}.
     *
     * @param role A desired role.
     */
    public DesiredRole(String role) {
        requireNonNull(role);
        value = role.trim();
    }

    /**
     * Returns true if a given string is a valid desired role.
     */
    public static boolean isValidDesiredRole(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DesiredRole // instanceof handles nulls
            && value.equals(((DesiredRole) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

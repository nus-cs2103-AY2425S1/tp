package hallpointer.address.model.session;

import static hallpointer.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Session's name in the application (not case-sensitive).
 * Guarantees: immutable; is valid as declared in {@link #isValidSessionName(String)}
 */
public class SessionName {

    public static final String MESSAGE_CONSTRAINTS =
            "Session names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the session name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String sessionName;

    /**
     * Constructs a {@code SessionName}.
     *
     * @param name A valid session name.
     */
    public SessionName(String name) {
        requireNonNull(name);
        checkArgument(isValidSessionName(name), MESSAGE_CONSTRAINTS);
        sessionName = name;
    }

    /**
     * Returns true if a given string is a valid session name.
     */
    public static boolean isValidSessionName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return sessionName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SessionName)) {
            return false;
        }

        SessionName otherSessionName = (SessionName) other;
        return sessionName.toLowerCase().equals(otherSessionName.sessionName.toLowerCase());
    }

    @Override
    public int hashCode() {
        return sessionName.hashCode();
    }

}

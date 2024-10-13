package seedu.address.model.issue;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Issue in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidIssueName(String)}
 */
public class Issue {

    public static final String MESSAGE_CONSTRAINTS = "Issues names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String issueName;

    /**
     * Constructs a {@code Issue}.
     *
     * @param issueName A valid issue name.
     */
    public Issue(String issueName) {
        requireNonNull(issueName);
        checkArgument(isValidIssueName(issueName), MESSAGE_CONSTRAINTS);
        this.issueName = issueName;
    }

    /**
     * Returns true if a given string is a valid issue name.
     */
    public static boolean isValidIssueName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Issue)) {
            return false;
        }

        Issue otherIssue = (Issue) other;
        return issueName.equals(otherIssue.issueName);
    }

    @Override
    public int hashCode() {
        return issueName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + issueName + ']';
    }

}

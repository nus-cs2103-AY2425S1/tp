package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class StarredStatus {
    public static final String MESSAGE_CONSTRAINTS = "Starred status should be true or false, " +
            "and it should not be blank";

    /*
     * StarredStatus should only be true or false.
     */
    public static final String VALIDATION_REGEX = "^(true|false)$";

    public final String value;

    /**
     * Constructs an {@code StarrredStatus}.
     *
     * @param isStarred A valid star status.
     */
    public StarredStatus(String isStarred) {
        requireNonNull(isStarred);
        checkArgument(isValidStarredStatus(isStarred), MESSAGE_CONSTRAINTS);
        value = isStarred;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStarredStatus(String test) {
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
        if (!(other instanceof StarredStatus)) {
            return false;
        }

        StarredStatus otherStarredStatus = (StarredStatus) other;
        return value.equals(otherStarredStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

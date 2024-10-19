package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Group {

    public static final String MESSAGE_CONSTRAINTS = "Group is written as g/group [number] (in lowercase)";
    public static final String VALIDATION_REGEX = "^group \\d+$";

    public final String groupName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param groupName A valid tag name.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidTagName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return groupName.equals(otherGroup.groupName);
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + groupName + ']';
    }

}

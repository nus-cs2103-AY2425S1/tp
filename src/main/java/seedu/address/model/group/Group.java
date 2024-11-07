package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents a Group in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {

    public static final String MESSAGE_CONSTRAINTS = "Group name should not be blank and is written as g/GROUP_NAME "
            + "(where GROUP_NAME is 100 characters or less including spaces between words)";
    public static final String VALIDATION_REGEX = "^.{1,100}$";
    private static final Logger logger = LogsCenter.getLogger(Group.class);
    public final String groupName;
    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid group name.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
        logger.info("A group is created: " + this.groupName);
    }

    /**
     * Returns true if a given string is a valid group name.
     */
    public static boolean isValidGroupName(String test) {
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
        return groupName.equalsIgnoreCase(otherGroup.groupName);
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    /**
     * Get group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + groupName + "]";
    }

}

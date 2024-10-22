package keycontacts.model.student;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's group in the student directory.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {
    public static final String NO_GROUP_STRING = "";
    public static final String MESSAGE_CONSTRAINTS = "Group name must not begin with a whitespace";
    /*
     * Group name is either an empty string (representing no group), or
     * the first character of the group name must not be a whitespace,
     */
    public static final String VALIDATION_REGEX = "^$|^[^\\s].*";

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
    }

    /**
     * Returns true if a given string is a valid group name.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if two groups are the same groups. Handles case where the group name is {@link #NO_GROUP_STRING}.
     */
    public boolean isSameGroup(Group otherGroup) {
        // same instance
        if (otherGroup == this) {
            return true;
        }

        // this group is a NO_GROUP -> i.e. this student is not in any group
        if (isNoGroup()) {
            return false;
        }

        return otherGroup.groupName.equals(groupName);
    }

    /**
     * Returns true if the group represents a NoGroup, false otherwise
     */
    public boolean isNoGroup() {
        return groupName.equals(NO_GROUP_STRING);
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
    @Override
    public String toString() {
        return groupName;
    }
}

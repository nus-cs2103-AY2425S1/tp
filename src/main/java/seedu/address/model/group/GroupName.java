package seedu.address.model.group;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class GroupName {

    public static final String MESSAGE_CONSTRAINTS =
            "GroupNames should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String groupName;

    /**
     * Constructs a {@code groupName}.
     *
     * @param groupName A valid group name.
     */
    public GroupName(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.group.GroupName)) {
            return false;
        }

        seedu.address.model.group.GroupName otherName = (seedu.address.model.group.GroupName) other;
        return groupName.equals(otherName.groupName);
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

}

package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Group name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class GroupName {

    public static final String MESSAGE_CONSTRAINTS =
        "Group Names must have 3 sections:\n"
            + "1. Course type - CS2103 or CS2103T\n"
            + "2. Tutorial Group - Any letter followed by a number\n"
            + "3. Group Number - Any number\n"
            + "e.g. CS2103T-W1-12 or CS2103-I2-9";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?i)(?<coursepart>CS2103T?)-(?<tutorialpart>[A-Z]\\d+)-"
        + "(?<grouppart>\\d+)$";

    private final String fullName;

    /**
     * Constructs a {@code GroupName}.
     *
     * @param name A valid name.
     */
    public GroupName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        name = formatGroupName(name);
        fullName = name.toUpperCase(); // leaving here for consistency through the app
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns String of {@code GroupName}.
     */
    public String getGroupName() {
        return fullName;
    }

    /**
     * Returns reformatted group name string, based on regex.
     */
    public static String formatGroupName(String groupName) {
        // this method is only called after validity of groupName is checked.
        assert isValidName(groupName) : "Validity of group name should have been checked already.";
        Pattern namePattern = Pattern.compile(VALIDATION_REGEX);
        Matcher matcher = namePattern.matcher(groupName);
        String coursePart = "";
        String tutorialPart = "";
        String groupPart = "";
        if (matcher.matches()) {
            coursePart = matcher.group("coursepart");
            tutorialPart = matcher.group("tutorialpart");
            groupPart = matcher.group("grouppart");
        }
        // removes leading 0s
        groupPart = Integer.toString(Integer.parseInt(groupPart));
        // removes leading 0 in number part
        tutorialPart = tutorialPart.charAt(0)
            + Integer.toString(Integer.parseInt(tutorialPart.substring(1)));
        return coursePart + "-" + tutorialPart + "-" + groupPart;
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
        if (!(other instanceof seedu.address.model.group.GroupName)) {
            return false;
        }

        seedu.address.model.group.GroupName otherName = (seedu.address.model.group.GroupName) other;
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

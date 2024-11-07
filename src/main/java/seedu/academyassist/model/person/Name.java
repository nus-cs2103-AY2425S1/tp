package seedu.academyassist.model.person;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.AppUtil;

/**
 * Represents a Person's name in the management system.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    private static final String SPECIAL_CHARACTERS = "-/'";
    public static final String MESSAGE_CONSTRAINTS =
            "Names should not be blank and should be between 2 and 255 characters long. Names should only contain "
                    + "alphabets, spaces and these special characters, excluding the parentheses, ("
                    + SPECIAL_CHARACTERS + "). Names should start and end with an alphabet, and there should not be "
                    + "more than one consecutive special characters.";

    public static final String VALIDATION_REGEX = "^[a-zA-Z](?!.*[" + SPECIAL_CHARACTERS + " ]{2})[a-zA-Z"
            + SPECIAL_CHARACTERS + " ]{0,253}[a-zA-Z]$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        AppUtil.checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
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

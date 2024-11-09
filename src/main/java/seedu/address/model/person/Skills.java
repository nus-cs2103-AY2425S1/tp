package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's skills in the TalentSG
 * Guarantees: immutable; is valid as declared in {@link #isValidSkillsString(String)}
 */
public class Skills {

    public static final String MESSAGE_CONSTRAINTS =
            "Skills should be a non-empty string consisting of alphabetic characters, separated by commas. "
                    + "Each skill should be between 1 and 40 characters long. We do also accept spacing, '#' and ':'";
    public static final String VALIDATION_REGEX =
            "^([A-Za-z0-9#:+]{1}[A-Za-z0-9#:+ ]{0,40})(, [A-Za-z0-9#:+]{1}[A-Za-z0-9#:+ ]{0,40})*$";
    public final String value;

    /**
     * Constructs a {@code Skills}.
     *
     * @param skills A valid skills string.
     */
    public Skills(String skills) {
        requireNonNull(skills);
        assert skills != null : "Skills should not be null";
        checkArgument(isValidSkillsString(skills), MESSAGE_CONSTRAINTS);
        value = skills;
    }

    /**
     * Returns true if a given string is a valid skill input.
     */
    public static boolean isValidSkillsString(String test) {
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
        if (!(other instanceof Skills)) {
            return false;
        }

        Skills otherSkills = (Skills) other;
        return value.equals(otherSkills.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

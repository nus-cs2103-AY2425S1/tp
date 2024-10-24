package seedu.address.model.skill;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Skill in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSkill(String)}
 */
public class Skill {

    public static final String MESSAGE_CONSTRAINTS = "Skills should be alphanumeric and cannot contain spaces";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String skill;

    /**
     * Constructs a {@code Skill}.
     *
     * @param skill A valid skill.
     */
    public Skill(String skill) {
        requireNonNull(skill);
        checkArgument(isValidSkill(skill), MESSAGE_CONSTRAINTS);
        this.skill = skill;
    }

    /**
     * Returns true if a given string is a valid skill.
     */
    public static boolean isValidSkill(String test) {
        // Null values are illegal values that should have been handled by
        // the command parser or the storage's json converter.
        assert test != null;

        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Skill)) {
            return false;
        }

        Skill otherTag = (Skill) other;
        return skill.equals(otherTag.skill);
    }

    @Override
    public int hashCode() {
        return skill.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + skill + ']';
    }

}

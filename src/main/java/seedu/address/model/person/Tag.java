package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Person's Tag in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTag(String)}
 */
public class Tag {

    /**
     * Enum representing the valid tag types for a person.
     * The available tag types are "Student" and "Tutor".
     *
     * <p>Each enum constant has a corresponding string representation that can be used
     * to compare tags, retrieve the role, and validate tag values.</p>
     */
    public enum TagType {
        STUDENT("Student"),
        TUTOR("Tutor");
        private final String role;
        /**
         * Constructs a {@code TagType} enum constant with the specified role.
         *
         * @param role The string representation of the tag type (e.g., "Student" or "Tutor").
         */
        TagType(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }

        /**
         * Checks if the given string is a valid tag by comparing it to the available tag types.
         *
         * @param test The string to be checked against the valid tag types.
         * @return {@code true} if the string matches any valid tag type, {@code false} otherwise.
         */
        public static boolean isValidTag(String test) {
            for (TagType tag : TagType.values()) {
                if (tag.role.equalsIgnoreCase(test)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Your tag should either be a student or tutor.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z]+$";
    public final TagType role;



    /**
     * Constructs a {@code Tag}.
     *
     * @param tag A valid tag.
     */
    public Tag(String tag) {
        requireNonNull(tag);
        checkArgument(isValidTag(tag), MESSAGE_CONSTRAINTS);
        this.role = TagType.valueOf(tag.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid tag.
     */
    public static boolean isValidTag(String test) {
        return test.matches(VALIDATION_REGEX) && TagType.isValidTag(test);
    }
    @Override
    public String toString() {
        return role.getRole();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }
        Tag otherTag = (Tag) other;
        return role.equals(otherTag.role);
    }
    @Override
    public int hashCode() {
        return role.hashCode();
    }
}

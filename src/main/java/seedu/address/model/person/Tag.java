package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.model.person.exceptions.InvalidTagException;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Tag in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTag(String)}
 */
public class Tag {
    public final String role;

    public static final ArrayList<String> VALIDTAGS = new ArrayList<>(Arrays.asList("Student", "Tutor"));

    public static final String MESSAGE_CONSTRAINTS =
            "Your tag should either be a student or tutor.";



    /**
     * Constructs a {@code Tag}.
     *
     * @param tag A valid tag.
     */
    public Tag(String tag) {
        requireNonNull(tag);
        if (!isValidTag(tag)) {
            throw new InvalidTagException(MESSAGE_CONSTRAINTS);
        }
        this.role = tag;
    }

    /**
     * Returns true if a given string is a valid tag.
     */
    public static boolean isValidTag(String test) {
        return VALIDTAGS.contains(test);
    }
    @Override
    public String toString() {
        return role;
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

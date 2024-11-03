package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a {@code Tag}'s name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TagName {

    public static final String MESSAGE_CONSTRAINTS =
            "Tag names should only contain alphanumeric characters and spaces, and they should not be blank";

    /*
     * The first character of the tag name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String tagName;

    /**
     * Constructs a {@code TagName}.
     * @param name A valid name for a tag.
     */
    public TagName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        tagName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return tagName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagName)) {
            return false;
        }

        TagName otherName = (TagName) other;
        return tagName.equalsIgnoreCase(otherName.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    public boolean matches(String validationRegex) {
        return tagName.matches(validationRegex);
    }
}

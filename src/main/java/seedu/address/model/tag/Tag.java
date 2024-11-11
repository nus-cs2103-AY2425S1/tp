package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.ParserUtil;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String BLOOD_TYPE_PREFIX = "Blood type ";
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric with "
            + "the exception of blood types (e.g A+, AB-, O+ etc.)";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}-+ ]+";
    public static final String BLOOD_TYPE_REGEX = "^(A|B|AB|O)[+-]$";

    public final String tagName;
    public final boolean isBloodType;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        if (ParserUtil.isValidBloodType(tagName)) {
            this.isBloodType = true;
        } else {
            this.isBloodType = false;
        }
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        if (test.contains("+") || test.contains("-")) {
            return test.matches(BLOOD_TYPE_REGEX);
        }
        return test.matches(VALIDATION_REGEX);
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
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}

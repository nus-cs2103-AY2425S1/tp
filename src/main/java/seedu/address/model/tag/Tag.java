package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.handler.DuplicatePhoneTagger;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC = "Tag names should be alphanumeric";
    public static final String MESSAGE_TAG_NAMES_CANNOT_BE_EMPTY = "Tag names cannot be empty.";
    public static final String MESSAGE_TAG_NAME_OR_VALUE_MISSING = "Tag name or value is missing";
    public static final String MESSAGE_TAG_NAME_IS_RESERVED = "Tag name is reserved: %1$s";
    public static final String VALIDATION_REGEX = "(\\p{Alpha}+|\\d+(\\.\\d+)?)";

    public final String tagName;
    public final String tagValue;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC);
        this.tagName = tagName;
        this.tagValue = null;
    }

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     * @param tagValue A valid tag value.
     */
    public Tag(String tagName, String tagValue) {
        requireNonNull(tagName);
        requireNonNull(tagValue);
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC);
        checkArgument(isValidTagName(tagValue), MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC);
        this.tagName = tagName;
        this.tagValue = tagValue;
    }
    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a reserved tag name.
     */
    public static boolean isReservedTagName(String test) {
        return test.equalsIgnoreCase(DuplicatePhoneTagger.DUPLICATE_PHONE_TAG_NAME);
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
        if (tagValue != null && otherTag.tagValue != null) {
            return tagName.equals(otherTag.tagName) && tagValue.equals(otherTag.tagValue);
        } else if (tagValue == null && otherTag.tagValue == null) {
            return tagName.equals(otherTag.tagName);
        } else {
            return false;
        }
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

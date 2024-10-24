package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {
    public static final Tag FAVOURITE_TAG = new Tag("favourite");
    public static final Tag BUYER_TAG = new Tag("buyer");

    public static final Tag SELLER_TAG = new Tag("seller");
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }
    /**
     * Static method to return instance of pre-defined tags or new tags when undefined name is used.
     * */
    public static Tag of(String tagName) {
        if (tagName.equalsIgnoreCase(FAVOURITE_TAG.getTagName())) {
            return FAVOURITE_TAG;
        }
        if (tagName.equalsIgnoreCase(BUYER_TAG.getTagName())) {
            return BUYER_TAG;
        }
        if (tagName.equalsIgnoreCase(SELLER_TAG.getTagName())) {
            return SELLER_TAG;
        }
        return new Tag(tagName);
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    public String getTagName() {
        return this.tagName;
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

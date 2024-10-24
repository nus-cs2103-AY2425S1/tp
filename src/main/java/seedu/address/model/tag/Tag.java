package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tag names should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final TagName tagName;
    private int taggedCount;

    /**
     * Constructs a {@code Tag}.
     * @param tagName A valid {@code TagName}.
     */
    public Tag(TagName tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
        this.taggedCount = 0;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if another tag has the same TagName as this tag.
     * @param otherTag A {@code tag} to compare with.
     */
    public boolean isSameTag(Tag otherTag) {
        if (otherTag == this) {
            return true;
        }

        return otherTag != null
                && otherTag.getTagName().equals(getTagName());
    }

    public TagName getTagName() {
        return tagName;
    }

    public int getNumberOfPersonsTagged() {
        return taggedCount;
    }

    public void increaseTaggedCount() {
        taggedCount++;
    }

    public void decreaseTaggedCount() {
        taggedCount--;
    }

    public int getTagCount() {
        return taggedCount;
    }
    /**
     * Returns true if the tag can be deleted.
     * The tag can be deleted if TaggedCount is 0.
     */
    public boolean canBeDeleted() {
        return taggedCount == 0;
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
    @Override
    public String toString() {
        return '[' + tagName.toString() + ']';
    }

}

package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tag names should be alphanumeric. "
            + "Tags that have multiple words can be separated by a hyphen (-)";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+(-\\p{Alnum}+)*";

    public final String tagName;
    private TagCategory tagCategory;
    private int occurrences = 0;

    /**
     * Constructs a {@code Tag}.
     * Category of the tag is {@code GENERAL} by default.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        this(tagName, TagCategory.GENERAL);
    }

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     * @param category A valid {@code TagCategory}.
     */
    public Tag(String tagName, TagCategory category) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        this.tagCategory = category;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Set the tag category.
     * @param tagCategory
     */
    public void setTagCategory(TagCategory tagCategory) {
        assert tagCategory != null;
        this.tagCategory = tagCategory;
    }

    /**
     * Returns the tag category.
     */
    public TagCategory getTagCategory() {
        return this.tagCategory;
    }

    /**
     * Returns the colour code corresponding to the tag category.
     * @return String containing the hex code for the colour.
     */
    public String getTagColour() {
        return this.tagCategory.getColorCode();
    }

    public int getOccurrences() {
        return occurrences;
    }

    /**
     * Tncrease the recorded occurrences by 1.
     */
    public void incrementOccurrences() {
        occurrences = occurrences + 1;
    }

    /**
     * Decrease the recorded occurrences by 1.
     */
    public void decrementOccurrences() {
        occurrences = occurrences - 1;
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

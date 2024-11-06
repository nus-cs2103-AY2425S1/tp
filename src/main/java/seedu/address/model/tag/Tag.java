package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags should be High Risk, Medium Risk or Low Risk";
    public static final String MESSAGE_FIELD_MESSAGE_FORMAT = "Person's Tag field is missing!";
    private static final String VALIDATION_REGEX = "(?i)high risk|medium risk|low risk";
    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        tagName = formatTagName(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.strip().matches(VALIDATION_REGEX);
    }

    /**
     * Formats the tag name to title case (e.g., "High Risk").
     */
    private String formatTagName(String tagName) {
        String trimmedTag = tagName.strip().toLowerCase();

        if ("high risk".equals(trimmedTag)) {
            return "High Risk";
        } else if ("medium risk".equals(trimmedTag)) {
            return "Medium Risk";
        } else if ("low risk".equals(trimmedTag)) {
            return "Low Risk";
        } else {
            return tagName; // Should not reach here if validation is used
        }
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
        return tagName.equalsIgnoreCase(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.toLowerCase().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return tagName;
    }

}

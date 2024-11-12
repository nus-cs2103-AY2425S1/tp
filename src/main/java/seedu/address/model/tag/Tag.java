package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags should be High Risk, Medium Risk or Low Risk";
    public static final String VALIDATION_REGEX = "high risk|medium risk|low risk";
    public static final String MESSAGE_FIELD_MESSAGE_FORMAT = "Person's Tag field is missing!";
    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        tagName = tagName.strip().toLowerCase();
        tagName = standardizeTagName(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = standardizeTagName(tagName);
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.toLowerCase().strip().matches(VALIDATION_REGEX);
    }

    /**
     * Standardizes the input tag name to a predefined format.
     *
     * <p>This method takes a tag name as input and standardizes it based on predefined mappings:
     * "low risk" becomes "Low Risk", "medium risk" becomes "Medium Risk", and any other input
     * is considered as "High Risk". This ensures consistent formatting of risk tags.</p>
     *
     * @param tagName The input tag to be standardized.
     * @return A standardized version of the input tag, either "Low Risk", "Medium Risk", or "High Risk".
     */
    public static String standardizeTagName(String tagName) {
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

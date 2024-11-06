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
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = standardizeTagName(tagName);
    }

    /**
     * Standardizes the input tag name to a predefined format.
     *
     * <p>This method takes a tag name as input and standardizes it based on predefined mappings:
     * "low risk" becomes "Low Risk", "medium risk" becomes "Medium Risk", and any other input
     * is considered as "High Risk". This ensures consistent formatting of risk tags.</p>
     *
     * @param tag The input tag to be standardized.
     * @return A standardized version of the input tag, either "Low Risk", "Medium Risk", or "High Risk".
     */
    public static String standardizeTagName(String tag) {
        if (tag.equals("low risk")) {
            return "Low Risk";
        }
        if (tag.equals("medium risk")) {
            return "Medium Risk";
        }
        return "High Risk";
    }


    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.toLowerCase().matches(VALIDATION_REGEX);
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
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
       return tagName;
    }

}

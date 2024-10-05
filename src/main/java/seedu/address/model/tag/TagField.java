package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a key-value pair for a tag field.
 * Both key and value must be alphanumeric, as enforced by the {@code VALIDATION_REGEX}.
 */
public class TagField {
    public static final String MESSAGE_CONSTRAINTS = "Tag field key and value should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String key;
    public final String value;

    /**
     * Constructs a {@code TagField}.
     *
     * @param key   A valid tag field key.
     * @param value A valid tag field value.
     */
    public TagField(String key, String value) {
        requireNonNull(key);
        requireNonNull(value);
        checkArgument(isValidTagField(key), MESSAGE_CONSTRAINTS);
        checkArgument(isValidTagField(value), MESSAGE_CONSTRAINTS);
        this.key = key;
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid tag field (key or value).
     */
    public static boolean isValidTagField(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagField)) {
            return false;
        }

        TagField otherTagField = (TagField) other;
        return key.equals(otherTagField.key) && value.equals(otherTagField.value);
    }

    @Override
    public int hashCode() {
        return (key + value).hashCode();
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}

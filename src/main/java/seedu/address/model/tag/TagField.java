package seedu.address.model.tag;

/* import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class TagField {
    public static final String MESSAGE_CONSTRAINTS = "Tag field key and value should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String key;
    public final String value;

    public TagField(String key, String value) {
        requireNonNull(key);
        requireNonNull(value);
        checkArgument(isValidTagField(key), MESSAGE_CONSTRAINTS);
        checkArgument(isValidTagField(value), MESSAGE_CONSTRAINTS);
        this.key = key;
        this.value = value;
    }

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
} */

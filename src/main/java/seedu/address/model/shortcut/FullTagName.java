package seedu.address.model.shortcut;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of a tag
 */
public class FullTagName {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]*[\\p{Alnum}]?$";
    private String fullTagName;

    /**
     * Creates instance of new TagName
     * @param fullTagName
     */
    public FullTagName(String fullTagName) {
        requireNonNull(fullTagName);
        checkArgument(isValidTagName(fullTagName), MESSAGE_CONSTRAINTS);
        this.fullTagName = fullTagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FullTagName)) {
            return false;
        }

        FullTagName otherFullTagName = (FullTagName) other;
        return fullTagName.equals(otherFullTagName.fullTagName);
    }

    @Override
    public int hashCode() {
        return fullTagName.hashCode();
    }
    @Override
    public String toString() {
        return fullTagName;
    }
}

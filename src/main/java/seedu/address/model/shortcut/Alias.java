package seedu.address.model.shortcut;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Creates a alias for a tag
 */
public class Alias {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]*[\\p{Alnum}]?$";
    private String alias;

    /**
     * creates an instance of an alias for a tag
     * @param alias
     */
    public Alias(String alias) {
        requireNonNull(alias);
        checkArgument(isValidAlias(alias), MESSAGE_CONSTRAINTS);
        this.alias = alias;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidAlias(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Alias)) {
            return false;
        }

        Alias otherAlias = (Alias) other;
        return alias.equals(otherAlias.alias);
    }

    @Override
    public int hashCode() {
        return alias.hashCode();
    }
    @Override
    public String toString() {
        return alias;
    }
}

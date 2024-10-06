package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's class in the address book.
 */
public class Class {
    public static final String MESSAGE_CONSTRAINTS =
            "Classes should only contain alphanumeric characters and spaces";

    public final String className;

    /**
     * Constructs a {@code Class}.
     *
     * @param className A valid class.
     */
    public Class(String className) {
        requireNonNull(className);
//      todo: checkArgument(isValidClassName(className), MESSAGE_CONSTRAINTS);
        this.className = className;
    }

    /**
     * Returns true if a given string is a valid class.
     */
// todo
//    public static boolean isValidClassName(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }

    @Override
    public String toString() {
        return className;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Class)) {
            return false;
        }

        Class otherName = (Class) other;
        return className.equals(otherName.className);
    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }
}

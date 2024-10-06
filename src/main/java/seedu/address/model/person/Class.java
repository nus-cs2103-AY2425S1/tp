package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

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

        Class otherClass = (Class) other;
        return className.equals(otherClass.className);
    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }
}

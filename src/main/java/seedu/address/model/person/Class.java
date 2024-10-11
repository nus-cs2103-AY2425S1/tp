package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's class in the address book.
 */
public class Class {

    public static final String MESSAGE_CONSTRAINTS =
            "Classes should only contain alphanumeric characters and spaces";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Class}.
     *
     * @param className A valid class.
     */
    public Class(String className) {
        requireNonNull(className);
        // todo: checkArgument(isValidClassName(className), MESSAGE_CONSTRAINTS);
        this.value = className;
    }

    /**
     * Returns true if a given string is a valid class name.
     */
    public static boolean isValidClass(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
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
        return value.equals(otherClass.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

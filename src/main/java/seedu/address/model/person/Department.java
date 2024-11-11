package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Department in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDepartment(String)}
 */
public class Department {

    public static final String MESSAGE_CONSTRAINTS = "Department can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Department}.
     *
     * @param department A valid department.
     */
    public Department(String department) {
        requireNonNull(department);
        checkArgument(isValidDepartment(department), MESSAGE_CONSTRAINTS);
        this.value = department;
    }

    /**
     * Returns true if a given string is a valid department.
     */
    public static boolean isValidDepartment(String test) {
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
        if (!(other instanceof Department)) {
            return false;
        }

        Department otherDepartment = (Department) other;
        return value.equals(otherDepartment.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

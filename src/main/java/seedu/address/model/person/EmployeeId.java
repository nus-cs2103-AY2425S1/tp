package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's employee id in the address book.
 * Guarantees: immutable; is always valid
 */
public class EmployeeId {
    public static final String MESSAGE_CONSTRAINTS =
            "Employee ID should only contain numeric characters";

    public final String value;

    /**
     * Constructs an {@code EmployeeId}.
     *
     * @param employeeId An employee id.
     */
    public EmployeeId(String employeeId) {
        requireNonNull(employeeId);
        checkArgument(isValidEmployeeId(employeeId), MESSAGE_CONSTRAINTS);
        value = employeeId;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid employee id.
     */
    public static boolean isValidEmployeeId(String test) {
        // Null values are illegal values that should have been handled by
        // the command parser or the storage's json converter.
        assert test != null;

        try {
            Integer.valueOf(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeId // instanceof handles nulls
                && value.equals(((EmployeeId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

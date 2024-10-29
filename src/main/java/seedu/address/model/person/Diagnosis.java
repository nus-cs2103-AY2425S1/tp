package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Diagnosis in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDiagnosis(String)}
 */
public class Diagnosis {
    public static final String MESSAGE_CONSTRAINTS =
            "DIAGNOSIS can only contain alphabets and the following special characters -> ,.()/- "
                    + "(e.g., - A. fib (Atrial Fibrillation).\n"
                    + "It can be an empty string at the point of initialisation, as diagnosis may not be done yet.\n";
    public static final String VALIDATION_MESSAGE = "Diagnosis must be between 1 and 100 characters long and can include alphanumeric characters, spaces, periods, parentheses, commas, dashes, and slashes. An empty string is also allowed.";
    public static final String VALIDATION_REGEX = "^(?:[A-Za-z0-9\\s.()/,-]{1,100}|)$";
    public final String value;

    /**
     * a
     * @param diagnosis
     */
    public Diagnosis(String diagnosis) {
        requireNonNull(diagnosis);
        checkArgument(isValidDiagnosis(diagnosis), MESSAGE_CONSTRAINTS);
        this.value = diagnosis;
    }

    public static boolean isValidDiagnosis(String test) {
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
        if (!(other instanceof Diagnosis)) {
            return false;
        }

        Diagnosis otherDiagnosis = (Diagnosis) other;
        return value.equals(otherDiagnosis.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

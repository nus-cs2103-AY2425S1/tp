package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Diagnosis in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDiagnosis(String)}
 */
public class Diagnosis {
    public static final String MESSAGE_CONSTRAINTS =
            "Diagnosis (if specified) must contain at least 1 alphabetic character and has a limit of 80 characters.";
    public static final String VALIDATION_REGEX = "^(?=.*[A-Za-z]).{1,80}$";
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
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
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

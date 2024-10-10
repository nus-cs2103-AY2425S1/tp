package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Diagnosis in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDiagnosis(String)}
 */
public class Diagnosis {
    public final String value;

    /**
     * a
     * @param diagnosis
     */
    public Diagnosis(String diagnosis) {
        requireNonNull(diagnosis);
        /*
        checkArgument(isValidDiagnosis(diagnosis), MESSAGE_CONSTRAINTS);

         */
        this.value = diagnosis;
    }

    public static boolean isValidDiagnosis(String test) {
        return true;
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

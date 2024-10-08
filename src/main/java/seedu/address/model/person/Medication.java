package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Medication in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedication(String)}
 */
public class Medication {
    private final String value;
    public Medication(String medication) {
        requireNonNull(medication);
//        checkArgument(isValidMedication(medication), MESSAGE_CONSTRAINTS);
        this.value = medication;
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
        if (!(other instanceof Medication)) {
            return false;
        }

        Medication otherMedication = (Medication) other;
        return value.equals(otherMedication.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

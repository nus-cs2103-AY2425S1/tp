package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class MedCon {

    public final String value;

    public MedCon(String medCon) {
        requireNonNull(medCon);
        value = medCon;
    }
    @Override
    public String toString() {
        return value;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedCon // instanceof handles nulls
                && value.equals(((MedCon) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
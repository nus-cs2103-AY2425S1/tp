package seedu.address.model.person;
import static java.util.Objects.requireNonNull;
/**
 * Represents a Patient's notes in WardWatch.
 * Guarantees: immutable; is always valid
 */
public class Notes {
    public final String value;

    /**
     * Constructs a {@code Notes}.
     *
     * @param patientNotes valid notes.
     */
    public Notes(String patientNotes) {
        requireNonNull(patientNotes);
        value = patientNotes;
    }
    @Override
    public String toString() {
        return value;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Notes // instanceof handles nulls
                && value.equals(((Notes) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

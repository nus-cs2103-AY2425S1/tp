package seedu.address.model.person;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's notes in WardWatch.
 * Guarantees: immutable; is always valid
 */
public class Notes {
    public static final String MESSAGE_CONSTRAINTS =
            "Notes field must have at least 1 alphanumeric character and has a character limit of 80.";
    public static final String VALIDATION_REGEX = "^(?=.*[A-Za-z0-9]).{1,80}$";
    public final String value;

    /**
     * Constructs a {@code Notes}.
     *
     * @param patientNotes valid notes.
     */
    public Notes(String patientNotes) {
        requireNonNull(patientNotes);
        checkArgument(isValidNote(patientNotes), MESSAGE_CONSTRAINTS);
        value = patientNotes;
    }

    public static boolean isValidNote(String test) {
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
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

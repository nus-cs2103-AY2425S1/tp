package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Student's note in the address book.
 * Guarantees: immutable; is always valid
 */
public class Note {

    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param note notes on the student.
     */
    public Note(String note) {
        requireNonNull(note);
        value = note;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && value.equals(((Note) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

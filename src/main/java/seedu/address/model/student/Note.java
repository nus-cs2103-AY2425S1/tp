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
        // short circuit if same object
        if (other == this) {
            return true;
        } else if (other instanceof Note) {
            Note otherNote = (Note) other;
            String thisValue = String.join(" ", value.split("\\s+"));
            String otherValue = String.join(" ", otherNote.value.split("\\s+"));
            return thisValue.equalsIgnoreCase(otherValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return String.join(" ", value.split("\\s+")).toLowerCase().hashCode();
    }
}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's notes in BizBook.
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS = "Notes should be alphanumeric.";
    public static final String VALIDATION_REGEX = "(?!^ +$)[\\p{Alnum} ]+";

    private String note;

    /**
     * Constructs {@code Notes}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        checkArgument(isValidNoteName(note), MESSAGE_CONSTRAINTS);
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNotes = (Note) other;

        return note.equals(otherNotes.note);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(note);
    }

    /**
     * Returns true if a given string is a valid note name.
     */
    public static boolean isValidNoteName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns all stored notes as a String.
     *
     * @return Notes string to be displayed in details pane
     */
    @Override
    public String toString() {
        return '[' + note + ']';
    }

    /**
     * Returns true if a note is empty and false if not.
     */
    public boolean isEmpty() {
        return note.isEmpty();
    }
}

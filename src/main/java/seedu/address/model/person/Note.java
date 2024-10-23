package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Person's notes in BizBook.
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS = "Notes should be alphanumeric.";
    public static final String DUPLICATE_MESSAGE_CONSTRAINTS = "There is already an existing note with this name.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";

    private String note;

    /**
     * Constructs {@code Notes}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
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

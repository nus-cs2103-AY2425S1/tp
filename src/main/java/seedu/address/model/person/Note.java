package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a note/remark given by the HR recruiters in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS =
            "Notes can take any value but should not be more than 200 characters long and it should not be blank";
    public static final int MAX_LENGTH = 200;

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A Note.
     */
    public Note(String note) {
        assert note != null : "Note cannot be null";
        requireNonNull(note);
        assert note.length() <= MAX_LENGTH : "Note exceeds maximum length of 200 characters";
        value = note;
    }

    /**
     * Returns true if a given string is less than 200 characters long
     */
    public static boolean isValidNote(String test) {
        assert test != null : "Test string for note validation cannot be null";
        return test.matches(VALIDATION_REGEX);
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

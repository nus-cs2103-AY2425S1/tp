package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a note/remark given by the HR recruiters in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS =
            "Notes should not be more than 200 characters long and it should not be blank";

    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A Note.
     */
    public Note(String note) {
        requireNonNull(note);
        value = note;
    }

    /**
     * Returns true if a given string is less than 200 characters long
     */
    public static boolean isValidNote(String test) {
        return (test.length() <= 200) && (!test.isEmpty());
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

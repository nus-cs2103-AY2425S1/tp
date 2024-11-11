package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's notes in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNotes(String)}
 */
public class Notes implements OptionalField {
    public static final String MESSAGE_CONSTRAINTS = "Notes can take any values, and can be empty";

    public final String value;

    private Notes() {
        this.value = "";
    }

    /**
     * Constructs a {@code Notes}.
     *
     * @param notes A valid notes string.
     */
    public Notes(String notes) {
        requireNonNull(notes);
        checkArgument(isValidNotes(notes), MESSAGE_CONSTRAINTS);
        value = notes.trim();
    }

    /**
     * Creates an empty Notes field.
     */
    public static Notes createEmpty() {
        return new Notes();
    }

    /**
     * Returns true if a given string is a valid notes.
     */
    public static boolean isValidNotes(String test) {
        return test != null;
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
        if (!(other instanceof Notes)) {
            return false;
        }
        Notes otherNotes = (Notes) other;
        // Treat empty notes as equivalent
        if (this.isEmpty() && otherNotes.isEmpty()) {
            return true;
        }
        return value.equals(otherNotes.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return value.isBlank();
    }

    @Override
    public String getValueForUi() {
        return isEmpty() ? "No" : "Yes";
    }
}

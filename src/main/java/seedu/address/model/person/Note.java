package seedu.address.model.person;

import java.time.LocalDateTime;

/**
 * Represents a Note in the address book.
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS = "Note must not be blank";

    private final String content;

    private final LocalDateTime timestamp;

    /**
     * Constructor for a Note
     * @param content the content of this Note
     */
    public Note(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Returns the content of this Note.
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the timestamp of this Note, which is the time it was created.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + content;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Note)) {
            return false;
        }
        Note otherNote = (Note) other;
        return otherNote.content.equals(this.content);
    }


}

package seedu.address.model.person;

import java.time.LocalDateTime;

/**
 * Represents a Note in the address book.
 */
public class Note {
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


}

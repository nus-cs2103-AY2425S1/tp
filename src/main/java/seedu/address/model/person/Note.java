package seedu.address.model.person;

import java.time.LocalDateTime;

public class Note {
    private final String content;

    private final LocalDateTime timestamp;

    public Note(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getConent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + content;
    }


}

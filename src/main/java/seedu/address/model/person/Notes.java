package seedu.address.model.person;

import java.util.List;

/**
 * Represents a Person's notes in BizBook.
 */
public class Notes {

    private List<String> notes;

    /**
     * Constructs {@code Notes}.
     *
     * @param notes A valid list of notes.
     */
    public Notes(List<String> notes) {
        this.notes = notes;
    }

    public List<String> getNotes() { return this.notes; }

    public void setNotes(List<String> notes) { this.notes = notes; }

    public void add(String note) {
        notes.add(note);
    }

    /**
     * Returns all stored notes as a String
     *
     * @return Notes string to be displayed in details pane
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String note : notes) {
            stringBuilder.append(note).append("\n");
        }

        return stringBuilder.toString();

    }

    /**
     * Returns true if the notes array is empty, false otherwise.
     */
    public boolean isEmpty() {
        return notes == null || notes.isEmpty();
    }


}

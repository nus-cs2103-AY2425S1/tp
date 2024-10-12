package seedu.address.model.person;

/**
 * Represents a Person's notes in BizBook.
 */
public class Notes {

    public final String[] notes;

    /**
     * Constructs {@code Notes}.
     *
     * @param notes A valid list of notes.
     */
    public Notes(String[] notes) {
        this.notes = notes;
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
            stringBuilder.append("• ").append(note).append("\n");
        }

        return stringBuilder.toString();

    }

    /**
     * Returns true if the notes array is empty, false otherwise.
     */
    public boolean isEmpty() {
        return notes == null || notes.length == 0;
    }


}

package bizbook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import bizbook.commons.exceptions.IllegalValueException;
import bizbook.model.person.Note;

/**
 * Jackson-friendly version of {@link Note}.
 */
public class JsonAdaptedNote {

    private final String noteName;

    /**
     * Constructs a {@code JsonAdaptedNote} with the given {@code noteName}.
     */
    @JsonCreator
    public JsonAdaptedNote(String noteName) {
        this.noteName = noteName;
    }

    /**
     * Converts a given {@code Note} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        noteName = source.getNote();
    }

    @JsonValue
    public String getNoteName() {
        return noteName;
    }

    /**
     * Converts this Jackson-friendly adapted note object into the model's {@code Note} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted note.
     */
    public Note toModelType() throws IllegalValueException {
        if (!Note.isValidNoteName(noteName)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(noteName);
    }
}

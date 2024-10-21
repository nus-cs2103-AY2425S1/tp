package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Note;

/**
 * Jackson-friendly version of {@link Note}.
 */
class JsonAdaptedNote {
    private final String content;

    /**
     * Constructs a {@code JsonAdaptedNote} with the given {@code content}.
     */
    @JsonCreator
    public JsonAdaptedNote(String content) {
        this.content = content;
    }

    /**
     * Converts a given {@code Note} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        content = source.getContent();
    }

    @JsonValue
    public String getContent() {
        return content;
    }

    /**
     * Converts this Jackson-friendly adapted note object into the model's {@code Note} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted note.
     */
    public Note toModelType() throws IllegalValueException {
        if (content == null) {
            throw new IllegalValueException(
                    String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, "Note content"));
        }
        return new Note(content);
    }


}

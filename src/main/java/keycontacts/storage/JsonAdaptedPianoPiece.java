package keycontacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.pianopiece.PianoPiece;

/**
 * Jackson-friendly version of {@link PianoPiece}.
 */
class JsonAdaptedPianoPiece {

    private final String pianoPieceName;

    /**
     * Constructs a {@code JsonAdaptedPianoPiece} with the given {@code pianoPieceName}.
     */
    @JsonCreator
    public JsonAdaptedPianoPiece(String pianoPieceName) {
        this.pianoPieceName = pianoPieceName;
    }

    /**
     * Converts a given {@code PianoPiece} into this class for Jackson use.
     */
    public JsonAdaptedPianoPiece(PianoPiece source) {
        pianoPieceName = source.pianoPieceName;
    }

    @JsonValue
    public String getPianoPieceName() {
        return pianoPieceName;
    }

    /**
     * Converts this Jackson-friendly adapted pianoPiece object into the model's {@code PianoPiece} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pianoPiece.
     */
    public PianoPiece toModelType() throws IllegalValueException {
        if (!PianoPiece.isValidPianoPieceName(pianoPieceName)) {
            throw new IllegalValueException(PianoPiece.MESSAGE_CONSTRAINTS);
        }
        return new PianoPiece(pianoPieceName);
    }

}

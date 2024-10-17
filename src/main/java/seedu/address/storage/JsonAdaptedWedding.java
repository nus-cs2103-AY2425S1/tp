package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
public class JsonAdaptedWedding {
    private final String weddingName;

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given {@code weddingName}.
     */
    @JsonCreator
    public JsonAdaptedWedding(String weddingName) {
        this.weddingName = weddingName;
    }

    /**
     * Converts a given {@code Wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        weddingName = source.getWeddingName().toString();
    }

    @JsonValue
    public String getWeddingName() {
        return weddingName;
    }

    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType() throws IllegalValueException {
        if (!Wedding.isValidWeddingName(weddingName)) {
            throw new IllegalValueException(WeddingName.MESSAGE_CONSTRAINTS);
        }
        return new Wedding(new WeddingName(weddingName));
    }
}

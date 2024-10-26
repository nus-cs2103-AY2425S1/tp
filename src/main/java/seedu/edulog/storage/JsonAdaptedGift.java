package seedu.edulog.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.model.gift.Gift;

/**
 * Jackson-friendly version of {@link Gift}.
 */
class JsonAdaptedGift extends Gift {


    /**
     * Constructs a {@code JsonAdaptedGift} with the given {@code giftName}.
     */
    @JsonCreator
    public JsonAdaptedGift(String giftName) {
        super(giftName);
    }

    /**
     * Converts a given {@code Gift} into this class for Jackson use.
     */
    public JsonAdaptedGift(Gift source) {
        super(source.giftName);
    }

    @JsonValue
    public String getGiftName() {
        return giftName;
    }

    /**
     * Converts this Jackson-friendly adapted gift object into the model's {@code Gift} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted gift.
     */
    public Gift toModelType() throws IllegalValueException {
        return new Gift(giftName);
    }

}

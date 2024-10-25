package seedu.address.storage.meetup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetup.AddedBuyer;

/**
 * Jackson-friendly version of {@link AddedBuyer}.
 */
public class JsonAdaptedAddedBuyer {

    private final String addedBuyerName;

    /**
     * Constructs a {@code JsonAdaptedAddedBuyer} with the given {@code addedBuyerName}.
     */
    @JsonCreator
    public JsonAdaptedAddedBuyer(String addedBuyerName) {
        this.addedBuyerName = addedBuyerName;
    }

    /**
     * Converts a given {@code AddedBuyer} into this class for Jackson use.
     */
    public JsonAdaptedAddedBuyer(AddedBuyer source) {
        addedBuyerName = source.addedBuyerName;
    }

    @JsonValue
    public String getAddedBuyerName() {
        return addedBuyerName;
    }

    /**
     * Converts this Jackson-friendly adapted addedBuyer object into the model's {@code AddedBuyer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted addedBuyer.
     */
    public AddedBuyer toModelType() throws IllegalValueException {
        if (!AddedBuyer.isValidBuyerName(addedBuyerName)) {
            throw new IllegalValueException(AddedBuyer.MESSAGE_CONSTRAINTS);
        }
        return new AddedBuyer(addedBuyerName);
    }

}

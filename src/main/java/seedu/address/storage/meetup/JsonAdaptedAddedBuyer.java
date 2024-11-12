package seedu.address.storage.meetup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetup.AddedBuyer;

/**
 * Jackson-friendly version of {@link AddedBuyer}.
 */
public class JsonAdaptedAddedBuyer {

    private final String fullName;

    /**
     * Constructs a {@code JsonAdaptedAddedBuyer} with the given {@code fullName}.
     */
    @JsonCreator
    public JsonAdaptedAddedBuyer(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Converts a given {@code AddedBuyer} into this class for Jackson use.
     */
    public JsonAdaptedAddedBuyer(AddedBuyer source) {
        fullName = source.fullName;
    }

    @JsonValue
    public String getAddedBuyerName() {
        return fullName;
    }

    /**
     * Converts this Jackson-friendly adapted addedBuyer object into the model's {@code AddedBuyer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted addedBuyer.
     */
    public AddedBuyer toModelType() throws IllegalValueException {
        if (!AddedBuyer.isValidName(fullName)) {
            throw new IllegalValueException(AddedBuyer.MESSAGE_CONSTRAINTS);
        }
        return new AddedBuyer(fullName);
    }

}

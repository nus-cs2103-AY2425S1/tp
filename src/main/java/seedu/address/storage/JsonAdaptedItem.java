package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.ItemName;

/**
 * Jackson-friendly version of {@link ItemName}.
 */
class JsonAdaptedItem {

    private final String itemName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code itemName}.
     */
    @JsonCreator
    public JsonAdaptedItem(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Converts a given {@code ItemName} into this class for Jackson use.
     */
    public JsonAdaptedItem(ItemName source) {
        itemName = source.value;
    }

    @JsonValue
    public String getItemName() {
        return itemName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ItemName} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item.
     */
    public ItemName toModelType() throws IllegalValueException {
        if (!ItemName.isValidItemName(itemName)) {
            throw new IllegalValueException(ItemName.MESSAGE_CONSTRAINTS);
        }
        return new ItemName(itemName);
    }

}

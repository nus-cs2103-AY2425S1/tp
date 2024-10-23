package spleetwaise.transaction.storage.adapters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import spleetwaise.address.commons.exceptions.IllegalValueException;
import spleetwaise.transaction.model.transaction.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    private final String category;

    /**
     * Constructs a {@code JsonAdaptedCategory} with the given {@code category}.
     */
    @JsonCreator
    public JsonAdaptedCategory(String category) {
        this.category = category;
    }

    /**
     * Converts a given {@code Category} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        category = source.category;
    }

    @JsonValue
    public String getCategory() {
        return category;
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code Category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Category toModelType() throws IllegalValueException {
        if (!Category.isValidTagName(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(category);
    }
}

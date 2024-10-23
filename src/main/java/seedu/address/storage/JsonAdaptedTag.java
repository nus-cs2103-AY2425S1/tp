package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagBuilder;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagValue;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagValue) {
        String[] parts = tagValue.split(":");
        String key = parts[0].trim();
        //Remove the empty space at index 0, and replace empty space with _ to simulate the tagging command
        String value = parts.length > 1 ? "_" + parts[1].stripLeading().replace("-", "_") : "";
        this.tagValue = key + value;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagValue = source.toString();
    }

    @JsonValue
    public String getTagName() {
        return tagValue;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalArgumentException {
        return new TagBuilder().build(tagValue);
    }

}

package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagName;
    private final String tagCategory;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName} and {@code tagCategory}.
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("tagName") String tagName,
                          @JsonProperty("tagCategory") String tagCategory) {
        this.tagName = tagName;
        this.tagCategory = tagCategory;
    }

    /**
     * Overloaded constructor for default tag creation.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this(tagName, "GENERAL");
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;
        tagCategory = source.getTagCategory().toString();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        TagCategory tagCat = TagCategory.fromString(tagCategory); // convert to TagCategory enum
        return new Tag(tagName, tagCat);
    }

}

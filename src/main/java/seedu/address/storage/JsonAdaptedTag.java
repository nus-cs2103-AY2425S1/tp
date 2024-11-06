package seedu.address.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {
    private final Map<String, String> map;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tag}.
     *
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @JsonCreator
    public JsonAdaptedTag(String tag) throws JsonParseException, JsonMappingException, IOException {
        Map<String, String> temporaryMap;
        if (tag.contains(":")) {
            try {
                TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
                };
                ObjectMapper mapper = new ObjectMapper();
                temporaryMap = mapper.readValue(tag, typeRef);
            } catch (JsonParseException e) {
                temporaryMap = new HashMap<>();
                temporaryMap.put(tag, null);
            }
            map = temporaryMap;

        } else {
            map = new HashMap<>();
            map.put(tag, null);
        }
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    @JsonCreator
    public JsonAdaptedTag(Tag source) {
        map = new HashMap<>();
        map.put(source.tagName, source.tagValue);
    }

    @JsonValue
    public String getTag() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);
        // return tagValue;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's
     * {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        Entry<String, String> entry = map.entrySet().iterator().next();
        if (!Tag.isValidTagName(entry.getKey())) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC);
        }
        if (entry.getValue() != null && Tag.isValidTagName(entry.getValue())) {
            return new Tag(entry.getKey(), entry.getValue());
        }
        return new Tag(entry.getKey());
    }

}

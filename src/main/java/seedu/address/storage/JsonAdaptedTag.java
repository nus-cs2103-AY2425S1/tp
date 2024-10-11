package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.clienttype.ClientType;

/**
 * Jackson-friendly version of {@link ClientType}.
 */
class JsonAdaptedTag {

    private final String clientTypeName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code clientTypeName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String clientTypeName) {
        this.clientTypeName = clientTypeName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(ClientType source) {
        clientTypeName = source.clientTypeName;
    }

    @JsonValue
    public String getClientTypeName() {
        return clientTypeName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ClientType toModelType() throws IllegalValueException {
        if (!ClientType.isValidClientTypeName(clientTypeName)) {
            throw new IllegalValueException(ClientType.MESSAGE_CONSTRAINTS);
        }
        return new ClientType(clientTypeName);
    }

}

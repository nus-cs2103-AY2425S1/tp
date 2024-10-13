package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.clienttype.ClientType;

/**
 * Jackson-friendly version of {@link ClientType}.
 */
class JsonAdaptedClientType {

    private final String clientTypeName;

    /**
     * Constructs a {@code JsonAdaptedclientType} with the given {@code clientTypeName}.
     */
    @JsonCreator
    public JsonAdaptedClientType(String clientTypeName) {
        this.clientTypeName = clientTypeName;
    }

    /**
     * Converts a given {@code clientType} into this class for Jackson use.
     */
    public JsonAdaptedClientType(ClientType source) {
        clientTypeName = source.clientTypeName;
    }

    @JsonValue
    public String getClientTypeName() {
        return clientTypeName;
    }

    /**
     * Converts this Jackson-friendly adapted client type object into the model's {@code clientType} object.
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

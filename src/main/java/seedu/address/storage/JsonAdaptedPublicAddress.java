package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.PublicAddressFactory;

/**
 * Jackson-friendly version of {@link PublicAddress}.
 */
class JsonAdaptedPublicAddress {

    private final String publicAddress;

    private final String label;

    /**
     * Constructs a {@code JsonAdaptedPublicAddress} with the given {@code address} & {@code label}.
     */
    @JsonCreator
    public JsonAdaptedPublicAddress(@JsonProperty("address") String publicAddress,
                                    @JsonProperty("label") String label) {
        this.publicAddress = publicAddress;
        this.label = label;
    }

    /**
     * Converts a given {@code PublicAddress} into this class for Jackson use.
     */
    public JsonAdaptedPublicAddress(PublicAddress source) {
        publicAddress = source.publicAddress;
        label = source.label;
    }

    /**
     * Converts this Jackson-friendly adapted public address object into the model's {@code PublicAddress} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted label.
     */
    public PublicAddress toModelType(Network network) throws IllegalValueException {
        try {
            return PublicAddressFactory.createPublicAddress(network, publicAddress, label);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }

}

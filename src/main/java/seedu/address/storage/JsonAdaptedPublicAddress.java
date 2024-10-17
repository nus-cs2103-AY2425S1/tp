package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.PublicAddressFactory;

/**
 * Jackson-friendly version of {@link PublicAddress}
 */
class JsonAdaptedPublicAddress {


    private final String address;

    private final String tag;

    /**
     * Constructs a {@code JsonAdaptedPublicAddress} with the given {@code address} & {@code tag}.
     */
    @JsonCreator
    public JsonAdaptedPublicAddress(@JsonProperty("address") String address, @JsonProperty("tag") String tag) {
        this.address = address;
        this.tag = tag;
    }

    /**
     * Converts a given {@code PublicAddress} into this class for Jackson use.
     */
    public JsonAdaptedPublicAddress(PublicAddress source) {
        address = source.address;
        tag = source.tag;
    }

    /**
     * Converts this Jackson-friendly adapted public address object into the model's {@code PublicAddress} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public PublicAddress toModelType(Network network) throws IllegalValueException {
        if (!PublicAddress.isValidPublicAddress(address) || !PublicAddress.isValidPublicAddressTag(tag)) {
            throw new IllegalValueException(PublicAddress.MESSAGE_CONSTRAINTS);
        }
        return PublicAddressFactory.createPublicAddress(network, address, tag);
    }

}

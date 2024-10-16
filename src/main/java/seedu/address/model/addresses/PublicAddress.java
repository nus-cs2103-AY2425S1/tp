package seedu.address.model.addresses;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a public address in the address book.
 */
public abstract class PublicAddress {

    public static final String DEFAULT_TAG = "default"; // TODO: Remove once placeholder is no longer needed

    public static final String MESSAGE_CONSTRAINTS =
            "Public Addresses can take any values, and it should not be blank"; // TODO: Update constraints

    public static final String NETWORK_CONSTRAINTS = "Network can only take values we support. For now: BTC";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_PA_REGEX = "[^\\s].*"; // TODO: Update regex
    public static final String VALIDATION_PAT_REGEX = "[^\\s].*"; // TODO: Update regex


    public final String address;

    public final String tag;

    /**
     * Constructs a {@code PublicAddress}
     *
     * @param address A valid public address
     * @param tag     An identifier for the PublicAddress
     */
    public PublicAddress(String address, String tag) {
        checkArgument(isValidPublicAddress(address), MESSAGE_CONSTRAINTS);
        this.address = address;
        this.tag = tag;
    }

    /**
     * Returns true if a given string is a valid public address.
     */
    public static boolean isValidPublicAddress(String test) {
        return test.matches(VALIDATION_PA_REGEX);
    }

    /**
     * Returns true if a given string is a valid public address tag.
     */
    public static boolean isValidPublicAddressTag(String test) {
        return test.matches(VALIDATION_PAT_REGEX);
    }

    public abstract Network getNetwork();

    /**
     * Checks if the network is valid
     *
     * @param network A string representation of the network
     * @return Network A enum of the network
     */
    public static Network isValidNetworkName(String network) {
        return Network.valueOf(network);
    }
    @Override
    public String toString() {
        return address;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PublicAddress)) {
            return false;
        }

        PublicAddress otherPublicAddress = (PublicAddress) other;
        return address.equals(otherPublicAddress.address) && tag.equals(otherPublicAddress.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, tag);
    }

}

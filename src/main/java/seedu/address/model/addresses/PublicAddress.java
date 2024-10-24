package seedu.address.model.addresses;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a public address in the address book.
 */
public abstract class PublicAddress {

    public static final String DEFAULT_LABEL = "default"; // TODO: Remove once placeholder is no longer needed

    public static final String MESSAGE_CONSTRAINTS =
            "Public Addresses can take any values, and it should not be blank"; // TODO: Update constraints

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_PA_REGEX = "[^\\s].*"; // TODO: Update regex
    public static final String VALIDATION_PAT_REGEX = "[^\\s].*"; // TODO: Update regex


    public final String publicAddress;

    public final String label;

    /**
     * Constructs a {@code PublicAddress}
     *
     * @param publicAddress A valid public address
     * @param label         An identifier for the PublicAddress
     */
    public PublicAddress(String publicAddress, String label) {
        checkArgument(isValidPublicAddress(publicAddress), MESSAGE_CONSTRAINTS);
        this.publicAddress = publicAddress;
        this.label = label;
    }

    /**
     * Returns true if a given string is a valid public address.
     */
    public static boolean isValidPublicAddress(String test) {
        return test.matches(VALIDATION_PA_REGEX);
    }

    /**
     * Returns true if a given string is a valid public address label.
     */
    public static boolean isValidPublicAddressLabel(String test) {
        return test.matches(VALIDATION_PAT_REGEX);
    }

    /**
     * Checks if the network is valid
     *
     * @param network A string representation of the network
     * @return Network A enum of the network
     */
    public static Network isValidNetworkName(String network) {
        return Network.valueOf(network);
    }

    public abstract Network getNetwork();

    @Override
    public String toString() {
        return label + ": " + publicAddress;
    }

    /**
     * Returns true if the public address string is the same as the other public address string
     *
     * @param otherPublicAddressString The other public address string
     * @return boolean
     */
    public boolean isPublicAddressStringEquals(String otherPublicAddressString) {
        return publicAddress.equals(otherPublicAddressString);
    }

    /**
     * Returns true if the public address string is part of the other public address string
     *
     * @param otherPublicAddressString The other public address string
     * @return boolean
     */
    public boolean isSubstringInPublicAddressString(String otherPublicAddressString) {
        return publicAddress.contains(otherPublicAddressString);
    }

    /**
     * Returns the public address string
     *
     * @return String
     */
    public String getPublicAddressString() {
        return publicAddress;
    }

    /**
     * Returns the label of the public address
     *
     * @return String
     */
    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PublicAddress otherPublicAddress)) {
            return false;
        }

        return publicAddress.equals(otherPublicAddress.publicAddress) && label.equals(otherPublicAddress.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicAddress, label);
    }

}

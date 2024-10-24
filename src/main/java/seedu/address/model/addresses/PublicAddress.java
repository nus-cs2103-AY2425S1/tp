package seedu.address.model.addresses;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a public address in the address book.
 */
public abstract class PublicAddress {

    public static final String DEFAULT_LABEL = "default"; // TODO: Remove once placeholder is no longer needed

    public static final String MESSAGE_LABEL_CONSTRAINTS =
            "Public Addresses can take any values, and it should not be blank"; // TODO: Update constraints

    public static final String VALIDATION_LABEL_REGEX = "[^\\s].*"; // TODO: Update regex

    public final String publicAddress;

    public final String label;

    /**
     * Constructs a {@code PublicAddress}
     *
     * @param publicAddress A valid public address
     * @param label         An identifier for the PublicAddress
     */
    public PublicAddress(String publicAddress, String label) {
        requireAllNonNull(publicAddress, label);
        checkArgument(isValidPublicAddressLabel(label), getMessageConstraints());

        if (!isValidPublicAddress(publicAddress)) {
            throw new IllegalArgumentException(getMessageConstraints());
        }

        this.publicAddress = publicAddress;
        this.label = label;
    }

    /**
     * Returns true if a given string is a valid public address label.
     */
    public static boolean isValidPublicAddressLabel(String test) {
        return test.matches(VALIDATION_LABEL_REGEX);
    }

    /**
     * Returns true if a given string is a valid public address.
     */
    protected abstract boolean isValidPublicAddress(String publicAddress);

    /**
     * Returns message for constraints of public address fields.
     */
    protected abstract String getMessageConstraints();

    /**
     * Returns the network type of the public address instance.
     */
    public abstract Network getNetwork();

    @Override
    public String toString() {
        return label + ": " + publicAddress;
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
        return publicAddress.equals(otherPublicAddress.publicAddress) && label.equals(otherPublicAddress.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicAddress, label);
    }

}

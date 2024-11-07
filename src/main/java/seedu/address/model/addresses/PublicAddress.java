package seedu.address.model.addresses;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_INVALID_CHAR;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_TOO_LONG;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND;

import java.util.Objects;

/**
 * Represents a public address in the address book.
 */
public abstract class PublicAddress {

    public static final String DEFAULT_LABEL = "default"; // TODO: Remove once placeholder is no longer needed

    public static final String MESSAGE_LABEL_CONSTRAINTS =
        "Public Addresses can take any values, and it should not be blank"; // TODO: Update constraints
    public static final String VALIDATION_PUBLIC_ADDRESS_REGEX = "^[a-zA-Z0-9]*$"; // TODO: Update regex
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

        validatePublicAddress(publicAddress);

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
     * @param publicAddress
     * @throws IllegalArgumentException
     */
    public static void validatePublicAddress(String publicAddress) throws IllegalArgumentException {
        if (publicAddress.length() > 100) { //length of public address too long
            throw new IllegalArgumentException(MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_TOO_LONG);
        } else if (!(publicAddress.matches(VALIDATION_PUBLIC_ADDRESS_REGEX))) {
            throw new IllegalArgumentException(MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_INVALID_CHAR);
        } else if (publicAddress.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND);
        }
    }

    /**
     * Returns true if a given string is a valid public address.
     */
    protected boolean isValidPublicAddress(String publicAddress) {
        return publicAddress.length() < 100 && publicAddress.matches(VALIDATION_PUBLIC_ADDRESS_REGEX)
            && !publicAddress.isEmpty();
    }

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

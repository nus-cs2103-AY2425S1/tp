package seedu.address.model.addresses;

/**
 * Represents a ETH public address in the address book.
 */
public class EthAddress extends PublicAddress {

    public static final String MESSAGE_CONSTRAINTS =
            "Public Addresses can take any values, and it should not be blank"; // TODO: Update constraints

    public static final String VALIDATION_PUBLIC_ADDRESS_REGEX = "[^\\s].*"; // TODO: Update regex

    /**
     * Constructs a {@code EthAddress}.
     *
     * @param publicAddress A valid public address.
     * @param label         A label for the public address.
     */
    public EthAddress(String publicAddress, String label) {
        super(publicAddress, label);
    }

    @Override
    protected boolean isValidPublicAddress(String publicAddress) {
        return publicAddress.matches(VALIDATION_PUBLIC_ADDRESS_REGEX);
    }

    @Override
    protected String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public Network getNetwork() {
        return Network.ETH;
    }

}

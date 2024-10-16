package seedu.address.model.addresses;

/**
 * Represents a BTC public address in the address book.
 */
public class BtcAddress extends PublicAddress {

    // Todo: Implement constraint
    public static final String MESSAGE_CONSTRAINTS
            = "Public Addresses can take any values, and it should not be blank"; // TODO: Update constraints

    public BtcAddress(String address, String tag) {
        super(address, tag);
    }

    /**
     * Returns true if a given string is a valid public address.
     */
    public static boolean isValidPublicAddress(String test) {
        // Todo: Implement validation
        return true;
    }

    @Override
    public Network getNetwork() {
        return Network.BTC;
    }

}

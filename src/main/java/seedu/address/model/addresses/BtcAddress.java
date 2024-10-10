package seedu.address.model.addresses;

/**
 * Represents a BTC public address in the address book.
 */
public class BtcAddress extends PublicAddress {

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

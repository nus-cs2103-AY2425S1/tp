package seedu.address.model.addresses;

/**
 * Represents a SOL public address in the address book.
 */
public class SolAddress extends PublicAddress {

    // Todo: Implement constraint
    public static final String MESSAGE_CONSTRAINTS =
            "Public Addresses can take any values, and it should not be blank"; // TODO: Update constraints

    /**
     * Constructs a {@code SolAddress}.
     * @param publicAddress A valid public address.
     * @param label A label for the public address.
     */
    public SolAddress(String publicAddress, String label) {
        super(publicAddress, label);

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
        return Network.SOL;
    }

}

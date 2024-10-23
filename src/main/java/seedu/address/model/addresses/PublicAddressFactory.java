package seedu.address.model.addresses;

/**
 * Factory class to create instances of PublicAddress based on the NetworkType.
 */
public class PublicAddressFactory {

    /**
     * Creates a new PublicAddress object based on the provided network.
     *
     * @param network the network type for which the public address is created
     * @param address the string representation of the public address
     * @param label the label associated with the address
     * @return a PublicAddress instance specific to the provided network
     * @throws IllegalArgumentException if the network type is unsupported
     */
    public static PublicAddress createPublicAddress(Network network, String address, String label) {
        switch (network) {
        case BTC:
            return new BtcAddress(address, label);
        case ETH:
            return new EthAddress(address, label);
        case SOL:
            return new SolAddress(address, label);
        default:
            throw new IllegalArgumentException("Unsupported network type: " + network);
        }
    }

}

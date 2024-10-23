package seedu.address.model.addresses;

/**
 * Represents supported cryptocurrency networks.
 */
public enum Network {
    BTC,
    ETH,
    SOL;

    public static final String MESSAGE_CONSTRAINTS = "Network not supported. Currently supported: BTC, ETH, SOL";
}

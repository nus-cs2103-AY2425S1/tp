package seedu.address.model.client;

/**
 * Represents the different types of clients in the system.
 * This enum provides two types of clients: BUYER and SELLER.
 * Each client type is associated with a specific string representation.
 */
public enum ClientTypes {

    /**
     * Represents a buyer client.
     */
    BUYER("buyer"),

    /**
     * Represents a seller client.
     */
    SELLER("seller");
    public static final String CLIENT_TYPE_CONSTRAINTS = "Client types should only be `BUYER` or `SELLER`";
    private final String type;

    /**
     * Constructs a {@code ClientTypes} with the specified type.
     *
     * @param type The string representation of the client type.
     */
    ClientTypes(String type) {
        this.type = type;
    }

    /**
     * Returns the string representation of the client type.
     *
     * @return The client type as a string.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns if a given string is a valid type.
     */
    public static boolean isValidClientType(String test) {
        if (test == null) {
            return false;
        }

        return test.equals(ClientTypes.BUYER.toString()) || test.equals(ClientTypes.SELLER.toString());
    }
}

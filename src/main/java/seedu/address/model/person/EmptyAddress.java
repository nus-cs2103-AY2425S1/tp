package seedu.address.model.person;

/**
 * Represents an empty Address of a Person in the address book.
 * Guarantees: immutable
 */
class EmptyAddress extends Address {

    public static final String INTERNAL_REPRESENTATION = "<REPRESENTATION FOR EMPTY ADDRESS>";

    private static final EmptyAddress emptyAddress = new EmptyAddress();

    private EmptyAddress() {
        super();
    }

    /**
     * Provides an object representing an empty email.
     */
    public static EmptyAddress get() {
        return emptyAddress;
    }

    /**
     * Returns true
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return INTERNAL_REPRESENTATION;
    }

    /**
     * Returns the String to be presented on the UI.
     */
    @Override
    public String getValueForUi() {
        return "No address provided";
    }
}

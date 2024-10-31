package seedu.address.model.person;

/**
 * Represents an empty Email of a Person in the address book.
 * Guarantees: immutable
 */
class EmptyEmail extends Email {

    public static final String INTERNAL_REPRESENTATION = "<REPRESENTATION FOR EMPTY EMAIL>";

    private static final EmptyEmail emptyEmail = new EmptyEmail();

    private EmptyEmail() {
        super();
    }

    /**
     * Provides an object representing an empty email.
     */
    public static EmptyEmail get() {
        return emptyEmail;
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
        return "No email provided";
    }
}

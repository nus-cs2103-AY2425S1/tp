package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an empty Email of a Person in the address book.
 * Guarantees: immutable
 */
class EmptyEmail extends Email {

    private static final EmptyEmail emptyEmail = new EmptyEmail();
    public static final String INTERNAL_REPRESENTATION = "<REPRESENTATION FOR EMPTY EMAIL>";

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
    public String getValueForUI() {
        return "No email provided";
    }
}

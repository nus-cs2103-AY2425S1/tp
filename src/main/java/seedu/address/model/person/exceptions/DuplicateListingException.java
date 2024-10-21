package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in a duplicate listing.
 * This exception is thrown when a user attempts to add a property listing
 * that already exists for a given person.
 */
public class DuplicateListingException extends RuntimeException {

    /**
     * Constructs a new DuplicateListingException with a default error message.
     */
    public DuplicateListingException() {
        super("Operation would result in duplicate listing for this person \n"
                + "Same address for 2 property Listings");
    }
}

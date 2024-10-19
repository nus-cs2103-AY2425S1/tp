package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Listing
 */
public class DuplicateListingException extends RuntimeException {
    public DuplicateListingException() {
        super("Operation would result in duplicate listing for this person \n" +
                "Same address for 2 property Listings");
    }
}

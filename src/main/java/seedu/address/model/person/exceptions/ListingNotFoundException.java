package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class ListingNotFoundException extends RuntimeException {
    public ListingNotFoundException(int index) {
        super("Listing with index " + (index + 1) + " does not exist.");
    }
}

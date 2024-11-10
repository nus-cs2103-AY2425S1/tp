package seedu.address.model.claim.exceptions;

/**
 * Signals that the operation will result in duplicate Claims.
 */
public class DuplicateClaimException extends RuntimeException {
    public DuplicateClaimException() {
        super("Operation would result in duplicate claims");
    }
}

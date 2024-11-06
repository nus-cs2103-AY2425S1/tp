package seedu.address.model.policy.exceptions;

/**
 * Signals that the operation will result in multiple policies of the same PolicyType.
 */
public class DuplicatePolicyTypeException extends RuntimeException {
    public DuplicatePolicyTypeException() {
        super("Operation would result in multiple policies of the same policy type");
    }
}

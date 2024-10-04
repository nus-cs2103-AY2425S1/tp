package seedu.address.model.car.exceptions;

/**
 * Signals that the operation will result in duplicate Cars (Cars are considered duplicates if they have the same
 * Vrn or Vin).
 */
public class DuplicateCarException extends RuntimeException {
    public DuplicateCarException() {
        super("Operation would result in duplicate cars");
    }
}

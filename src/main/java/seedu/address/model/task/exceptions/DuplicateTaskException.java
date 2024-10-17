package seedu.address.model.task.exceptions;

/**
 * Signals that the operation will result in duplicate Tasks (Tasks are considered duplicates if they have the same
 * identity).
 */
public class DuplicateTaskException extends RuntimeException {
}

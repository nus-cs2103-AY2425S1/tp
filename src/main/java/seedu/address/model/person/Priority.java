package seedu.address.model.person;

/**
 * Represents a Person's priority in the address book.
 * Guarantees: immutable; is always valid
 */
public enum Priority {
    HIGH, MEDIUM, LOW;

    public static final String MESSAGE_CONSTRAINTS = "Priority should be HIGH, MEDIUM, or LOW";
}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's relationship in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRelationship(String)}
 */
public class Relationship {

    public static final String MESSAGE_CONSTRAINTS =
            "Relationships should only contain alphabetic characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[A-Za-z]+$";
    public final String relationship;

    /**
     * Constructs an {@code Relationship}.
     *
     * @param relationship A valid address.
     */
    public Relationship(String relationship) {
        requireNonNull(relationship);
        checkArgument(isValidRelationship(relationship), MESSAGE_CONSTRAINTS);
        this.relationship = relationship;
    }

    /**
     * Returns true if a given string is a valid relationship.
     */
    public static boolean isValidRelationship(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.relationship;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Relationship)) {
            return false;
        }

        Relationship otherRelationship = (Relationship) other;
        return relationship.equals(otherRelationship.relationship);
    }

    @Override
    public int hashCode() {
        return this.relationship.hashCode();
    }
}

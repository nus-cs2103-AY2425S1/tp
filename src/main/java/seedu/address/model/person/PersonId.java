package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.UUID;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's unique ID in the address book.
 */
public class PersonId {
    public static final String MESSAGE_CONSTRAINTS = "PersonId must conform to Java UUID format";
    private final UUID id;

    /**
     * Creates a new PersonId with a randomly generated UUID
     */
    public PersonId() {
        this.id = UUID.randomUUID();
    }

    /**
     * Creates a new PersonId with a given UUID
     */
    public PersonId(UUID id) {
        this.id = id;
    }

    /**
     * Creates a new PersonId with a UUID created from a given string
     */
    public PersonId(String id) {
        requireNonNull(id);
        checkArgument(isValidPersonId(id), MESSAGE_CONSTRAINTS);
        this.id = UUID.fromString(id);
    }

    /**
     * Returns true if a given string is a valid PersonId
     * @param idString
     * @return
     */
    public static boolean isValidPersonId(String idString) {
        try {
            UUID.fromString(idString);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonId) {
            PersonId other = (PersonId) obj;
            return id.equals(other.id);
        }
        return false;
    }
}

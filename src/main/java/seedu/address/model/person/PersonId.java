package seedu.address.model.person;

import java.util.UUID;

/**
 * Represents a Person's unique ID in the address book.
 */
public class PersonId {
    private final UUID id;

    /**
     * Creates a new PersonID with a randomly generated UUID
     */
    public PersonId() {
        this.id = UUID.randomUUID();
    }

    /**
     * Creates a new PersonID with a given UUID
     */
    public PersonId(UUID id) {
        this.id = id;
    }

    /**
     * Creates a new PersonID with a UUID created from a given string
     */
    public PersonId(String id) {
        this.id = UUID.fromString(id);
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

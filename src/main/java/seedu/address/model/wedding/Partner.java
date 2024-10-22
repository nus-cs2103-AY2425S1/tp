package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents a main character of a Wedding.
 */
public abstract class Partner {
    private Name nameToUse;
    private Person person;

    /**
     * Constructs a {@code Partner}.
     *
     */
    public Partner(String name) {
        requireNonNull(name);
        this.nameToUse = new Name(name);
        this.person = null;
    }

    public Partner(String name, Person person) {
        requireNonNull(name);
        requireNonNull(person);
        this.nameToUse = new Name(name);
        this.person = person;
    }

    public Name getNameToUse() {
        return nameToUse;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        requireNonNull(person);
        this.person = person;
    }

    @Override
    public String toString() {
        return person.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Husband)) {
            return false;
        }

        Partner otherPartner = (Partner) other;
        return person.equals(otherPartner.person);
    }

    @Override
    public int hashCode() {
        return person.hashCode();
    }
}

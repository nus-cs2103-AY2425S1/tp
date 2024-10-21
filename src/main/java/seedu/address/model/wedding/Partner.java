package seedu.address.model.wedding;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

public abstract class Partner {
    private Name nameToUse;
    private Person person;

    public Partner(String name) {
        requireNonNull(name);
        this.nameToUse = new Name(name);
        this.person = null;
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

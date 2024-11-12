package seedu.address.model.concert;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represent association between a person and concert in the address book
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class ConcertContact {
    private final Person person;
    private final Concert concert;

    /**
     * Constructs Concert Contact.
     *
     * @param person Person in the association
     * @param concert Concert in the association
     */
    public ConcertContact(Person person, Concert concert) {
        requireAllNonNull(person, concert);
        this.person = person;
        this.concert = concert;
    }

    public Person getPerson() {
        return person;
    }

    public Concert getConcert() {
        return concert;
    }

    public boolean isAssociated(Concert otherConcert) {
        return concert.equals(otherConcert);
    }

    public boolean isAssociated(Person otherPerson) {
        return person.equals(otherPerson);
    }

    public boolean isSameConcertContact(ConcertContact otherConcertContact) {
        return equals(otherConcertContact);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConcertContact)) {
            return false;
        }

        ConcertContact otherContact = (ConcertContact) other;
        return otherContact.person.equals(person)
                && otherContact.concert.equals(concert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, concert);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("concert", concert)
                .add("person", person).toString();
    }

}

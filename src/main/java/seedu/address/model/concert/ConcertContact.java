package seedu.address.model.concert;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.address.model.person.Person;

/**
 * Represent association between a person and concert in the address book
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ConcertContact {
    private static final Map<Concert, ArrayList<ConcertContact>> concertContacts = new HashMap<>();
    private final Person person;
    private final Concert concert;

    /**
     * Constructs Concert Contact
     *
     * @param person
     * @param concert
     */
    public ConcertContact(Person person, Concert concert) {
        requireAllNonNull(person, concert);

        this.person = person;
        this.concert = concert;

        addContactToConcert(this); //instantly adds Person to Concert list as ConcertContact created
    }

    public Person getPerson() {
        return person;
    }
    public Concert getConcert() {
        return concert;
    }

    /**
     * Adds ConcertContact to Concert list in the map, based on concert linked to Person
     *
     * @param contact
     */
    private static void addContactToConcert(ConcertContact contact) {
        concertContacts.computeIfAbsent(contact.getConcert(), key -> new ArrayList<>()).add(contact);
    }

    /**
     * Returns the list of ConcertContacts in the Concert
     *
     * @return List of ConcertContact
     */
    public ArrayList<ConcertContact> getConcertContactList(Concert concert) {
        return concertContacts.get(concert);
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
        return otherContact != null && otherContact.person.equals(person)
                && otherContact.concert.equals(concert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, concert);
    }

    @Override
    public String toString() {
        return person.getName() + " is a " + person.getRole() + " for " + concert.getName();
    }

}

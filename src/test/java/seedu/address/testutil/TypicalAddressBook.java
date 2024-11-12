package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * A utility class to generate AddressBooks
 */
public abstract class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all typical concerts and persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Concert concert : TypicalConcerts.getTypicalConcerts()) {
            ab.addConcert(concert);
        }
        for (ConcertContact concertContact : TypicalConcertContacts.getTypicalConcertContacts()) {
            ab.addConcertContact(concertContact);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons only.
     */
    public static AddressBook getTypicalAddressBookPersons() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical concerts only.
     */
    public static AddressBook getTypicalAddressBookConcerts() {
        AddressBook ab = new AddressBook();
        for (Concert concert : TypicalConcerts.getTypicalConcerts()) {
            ab.addConcert(concert);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical concertContacts only.
     */
    public static AddressBook getTypicalAddressBookConcertContacts() {
        AddressBook ab = new AddressBook();
        for (ConcertContact concertContact : TypicalConcertContacts.getTypicalConcertContacts()) {
            ab.addConcertContact(concertContact);
        }
        return ab;
    }
}

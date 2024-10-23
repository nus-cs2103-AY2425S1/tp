package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain any duplicate
     * persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the concerts list. This list will not contain any duplicate
     * concerts.
     */
    ObservableList<Concert> getConcertList();

    /**
     * Returns an unmodifiable view of the concertContact list. This list will not contain any duplicate
     * concertContacts.
     */
    ObservableList<ConcertContact> getConcertContactList();

}

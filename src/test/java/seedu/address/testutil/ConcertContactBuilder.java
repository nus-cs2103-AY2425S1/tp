package seedu.address.testutil;

import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalPersons.ALICE;

import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building ConcertContact objects.
 */
public class ConcertContactBuilder {
    private Person person;
    private Concert concert;

    /**
     * Creates a {@code ConcertContactBuilder} with the default details.
     */
    public ConcertContactBuilder() {
        person = new PersonBuilder(ALICE).build();
        concert = new ConcertBuilder(COACHELLA).build();
    }

    /**
     * Creates the ConcertContactBuilder with the data of {@code concertContactToCopy}
     * @param concertContactToCopy
     */
    public ConcertContactBuilder(ConcertContact concertContactToCopy) {
        person = concertContactToCopy.getPerson();
        concert = concertContactToCopy.getConcert();
    }

    /**
     * Sets the {@code Person} of the {@code ConcertContact}
     */
    public ConcertContactBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Sets the {@code Concert} of the {@code ConcertContact}
     */
    public ConcertContactBuilder withConcert(Concert concert) {
        this.concert = concert;
        return this;
    }

    public ConcertContact build() {
        return new ConcertContact(person, concert);
    }
}

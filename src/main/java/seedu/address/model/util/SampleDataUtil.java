package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.concert.ConcertDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    // Sample Persons
    public static final Person SAMPLE_PERSON_ALEX = new Person(new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
            new Role("organiser"), getTagSet("friends"));
    public static final Person SAMPLE_PERSON_BERNICE = new Person(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            new Role("artist"), getTagSet("colleagues", "friends"));
    public static final Person SAMPLE_PERSON_CHARLOTTE = new Person(new Name("Charlotte Oliveiro"),
            new Phone("93210283"), new Email("charlotte@example.com"),
            new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Role("promoter"),
            getTagSet("neighbours"));
    public static final Person SAMPLE_PERSON_DAVID = new Person(new Name("David Li"), new Phone("91031282"),
            new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            new Role("organiser"), getTagSet("family"));
    public static final Person SAMPLE_PERSON_IRFAN = new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
            new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"), new Role("promoter"),
            getTagSet("classmates"));
    public static final Person SAMPLE_PERSON_ROY = new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
            new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"), new Role("artist"),
            getTagSet("colleagues"));

    // Sample Concerts
    public static final Concert SAMPLE_CONCERT_COACHELLA = new Concert(new Name("Coachella"),
            new Address("Blk 30 Geylang Street 29, #06-40"), new ConcertDate("2024-04-12 0000"));
    public static final Concert SAMPLE_CONCERT_GLASTONBURY = new Concert(new Name("Glastonbury"),
            new Address("1 Stadium Dr, Singapore 397629"), new ConcertDate("2024-10-10 2200"));
    public static final Concert SAMPLE_CONCERT_JUSTICEWORLD = new Concert(new Name("Justice World"),
            new Address("National Stadium, 1 Stadium Dr, Singapore 397629"), new ConcertDate("2024-12-08 1900"));
    public static final Concert SAMPLE_CONCERT_THEERAS = new Concert(new Name("The Eras"),
            new Address("Wembley Stadium, Wembley, London HA9 0WS, United Kingdom"),
            new ConcertDate("2024-08-15 2000"));
    public static final Concert SAMPLE_CONCERT_TOMORROWLAND = new Concert(new Name("Tomorrowland"),
            new Address("De Schorre Recreation Ground, Boom 2850 Belgium"), new ConcertDate("2024-07-19 0000"));

    // Sample ConcertContacts
    public static final ConcertContact SAMPLE_CONCERTCONTACT_ALEX_COACHELLA = new ConcertContact(
            SAMPLE_PERSON_ALEX, SAMPLE_CONCERT_COACHELLA
    );
    public static final ConcertContact SAMPLE_CONCERTCONTACT_ALEX_GLASTONBURY = new ConcertContact(
            SAMPLE_PERSON_ALEX, SAMPLE_CONCERT_GLASTONBURY
    );
    public static final ConcertContact SAMPLE_CONCERTCONTACT_BERNICE_COACHELLA = new ConcertContact(
            SAMPLE_PERSON_BERNICE, SAMPLE_CONCERT_COACHELLA
    );
    public static final ConcertContact SAMPLE_CONCERTCONTACT_CHARLOTTE_THEERAS = new ConcertContact(
            SAMPLE_PERSON_CHARLOTTE, SAMPLE_CONCERT_THEERAS
    );
    public static final ConcertContact SAMPLE_CONCERTCONTACT_CHARLOTTE_TOMORROWLAND = new ConcertContact(
            SAMPLE_PERSON_CHARLOTTE, SAMPLE_CONCERT_TOMORROWLAND
    );

    public static Person[] getSamplePersons() {
        return new Person[] {
            SAMPLE_PERSON_ALEX,
            SAMPLE_PERSON_BERNICE,
            SAMPLE_PERSON_CHARLOTTE,
            SAMPLE_PERSON_DAVID,
            SAMPLE_PERSON_IRFAN,
            SAMPLE_PERSON_ROY
        };
    }

    public static Concert[] getSampleConcerts() {
        return new Concert[] {
            SAMPLE_CONCERT_COACHELLA,
            SAMPLE_CONCERT_GLASTONBURY,
            SAMPLE_CONCERT_JUSTICEWORLD,
            SAMPLE_CONCERT_THEERAS,
            SAMPLE_CONCERT_TOMORROWLAND
        };
    }

    public static ConcertContact[] getSampleConcertContacts() {
        return new ConcertContact[] {
            SAMPLE_CONCERTCONTACT_ALEX_COACHELLA,
            SAMPLE_CONCERTCONTACT_ALEX_GLASTONBURY,
            SAMPLE_CONCERTCONTACT_BERNICE_COACHELLA,
            SAMPLE_CONCERTCONTACT_CHARLOTTE_THEERAS,
            SAMPLE_CONCERTCONTACT_CHARLOTTE_TOMORROWLAND
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Concert sampleConcert : getSampleConcerts()) {
            sampleAb.addConcert(sampleConcert);
        }
        for (ConcertContact sampleConcertContact : getSampleConcertContacts()) {
            sampleAb.addConcertContact(sampleConcertContact);
        }

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

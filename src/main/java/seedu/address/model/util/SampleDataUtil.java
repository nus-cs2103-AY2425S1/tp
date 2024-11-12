package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            Person.createPerson(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("Celebrity")),
            Person.createPerson(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("Celebrity")),
            Person.createPerson(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("Hairdresser", "MakeupArtist")),
            Person.createPerson(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("CameraMan")),
            Person.createPerson(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("CameraMan")),
            Person.createPerson(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("Chauffeur")),
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            Event.createEvent(new EventName("Oscars"), new Time(LocalDateTime.parse("2015-02-20T06:30:00"),
                    LocalDateTime.parse("2015-02-20T07:30:00")),
                new Venue("Hollywood"), getSamplePersons()[0],
                Set.of(getSamplePersons()[2], getSamplePersons()[3], getSamplePersons()[4])),
            Event.createEvent(new EventName("Photo Shoot"), new Time(LocalDateTime.parse("2015-03-20T06:30:00"),
                    LocalDateTime.parse("2015-03-20T07:30:00")),
                new Venue("Park"), getSamplePersons()[1],
                Set.of(getSamplePersons()[2], getSamplePersons()[3], getSamplePersons()[4])),
            Event.createEvent(new EventName("Brand Event"), new Time(LocalDateTime.parse("2015-04-20T06:30:00"),
                    LocalDateTime.parse("2015-04-20T07:30:00")),
                new Venue("Mall"), getSamplePersons()[1],
                Set.of(getSamplePersons()[2], getSamplePersons()[3], getSamplePersons()[4])),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
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

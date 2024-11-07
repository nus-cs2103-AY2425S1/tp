package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyWeddingBook;
import seedu.address.model.WeddingBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Job("Caterer"),
                    getTagSet("Mary & John")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Job("Photographer"),
                    getTagSet("Mary & John", "Anthony & Jane")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Job("Usher"),
                     getTagSet("Ahmad Bin Ali & Siti Bte Rahim")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Job("Security"),
                    getTagSet("Devesh s/o Ramesh & Priya d/o Ramesh")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Job("Videographer"),
                    getTagSet("Carol & John")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Job("Audio"),
                    getTagSet("Fiona & John"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
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

    /**
     * Returns a set of participants containing the list of strings given.
     */
    public static Set<Person> getParticipantSet(Person... person) {
        return Arrays.stream(person)
                .collect(Collectors.toSet());
    }

    public static Wedding[] getSampleWeddings() {
        return new Wedding[] {
            new Wedding(new WeddingName("Adam & Steve"), new Venue("Serangoon Nex"),
                    new Date("22/12/2025")),
            new Wedding(new WeddingName("Lisa & Jane"), new Venue("Hougang Void Deck"),
                    new Date("11/11/2032"))
        };
    }

    public static ReadOnlyWeddingBook getSampleWeddingBook() {
        WeddingBook sampleWb = new WeddingBook();
        for (Wedding sampleWedding : getSampleWeddings()) {
            sampleWb.addWedding(sampleWedding);
        }
        return sampleWb;
    }

}

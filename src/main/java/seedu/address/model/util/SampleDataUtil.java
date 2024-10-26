package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new Role("friends"), null),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Role("colleagues"), null),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Role("neighbours"), null),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Role("family"), null),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new Role("classmates"), null),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Role("colleagues"), null),
            new Person(new Name("Gourmet Bites Catering"), new Phone("84362714"), new Email("gourmetbites@example.com"),
                    new Address("123 Orchard Road, #05-67"), new Role("FoodCaterer"), null),
            new Person(new Name("Timeless Moments Photograph"), new Phone("82336242"),
                    new Email("timelessmoments@example.com"),
                    new Address("456 Marine Parade, #08-34"), new Role("Photographer"), null),
            new Person(new Name("Petal Pusher Florals"), new Phone("91234852"), new Email("petalpusher@example.com"),
                    new Address("321 Holland Road, #01-12"), new Role("Florist"), null),
            new Person(new Name("Blossom & Vine"), new Phone("80472642"), new Email("blossomvine@example.com"),
                    new Address("135 Tanjong Pagar Road, #02-58"), new Role("Florist"), null),
            new Person(new Name("Glam Squad"), new Phone("89373673"), new Email("glamsquad@example.com"),
                    new Address("678 Serangoon Road, #10-23"), new Role("MakeUp"), null)
        };
    }

    public static Wedding[] getSampleWeddings() {
        return new Wedding[] {
            new Wedding(new Name("Alex's Wedding"), null, null),
            new Wedding(new Name("Bernice's Wedding"), new Date("2024-12-12"), null),
            new Wedding(new Name("Charlotte's Wedding"), null, new Venue("Grand Hyatt")),
            new Wedding(new Name("David's Wedding"), new Date("2025-06-01"), new Venue("Marina Bay Sands"))
        };
    }

    public static void setUpWeddings(Person[] persons, Wedding[] weddings) {
        // Set up clients
        weddings[0].setClient(persons[0]);
        weddings[1].setClient(persons[1]);
        weddings[2].setClient(persons[2]);
        weddings[3].setClient(persons[3]);
        // Set up jobs
        persons[7].addWeddingJob(weddings[0]);
        persons[0].addWeddingJob(weddings[1]);
        persons[0].addWeddingJob(weddings[2]);
        persons[6].addWeddingJob(weddings[0]);
        persons[6].addWeddingJob(weddings[1]);
        persons[8].addWeddingJob(weddings[1]);
        persons[8].addWeddingJob(weddings[2]);
        persons[9].addWeddingJob(weddings[3]);
        persons[10].addWeddingJob(weddings[3]);
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        Person[] persons = getSamplePersons();
        Wedding[] weddings = getSampleWeddings();
        setUpWeddings(persons, weddings);
        for (Person samplePerson : persons) {
            sampleAb.addPerson(samplePerson);
        }
        for (Wedding wedding : weddings) {
            sampleAb.addWedding(wedding);
        }
        return sampleAb;
    }


}

package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.GradYear;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("+65 87438807"), new Email("alexyeoh@example.com"),
                new RoomNumber("01-0123"), new Address("Apple Dorm Block A"),
                new EmergencyContact(new Name("Aiken"), new Phone("81234567")), new GradYear("2027"),
                getTagSet("Floor1")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new RoomNumber("01-0124"), new Address("Apple Dorm Block B"),
                new EmergencyContact(new Name("Aiken"), new Phone("+20 81234567")), new GradYear("2028"),
                getTagSet("Floor1", "volleyball")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new RoomNumber("02-0123"), new Address("Mango Dorm Block C"),
                new EmergencyContact(new Name("Dueet"), new Phone("+123 23 12345678")), new GradYear("2029"),
                getTagSet("Floor10")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new RoomNumber("08-8888"), new Address("Pear Dorm Block A"),
                new EmergencyContact(new Name("Benny Yao"), null), new GradYear("2030"),
                getTagSet("Floor3")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new RoomNumber("04-4444"), new Address("Grape Dorm Block A"),
                new EmergencyContact(null, new Phone("+20 81234567")), new GradYear("2027"),
                getTagSet("Floor5")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                null, new Address("Grape Dorm Block A"), null, null,
                getTagSet("Floor3"))
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

}

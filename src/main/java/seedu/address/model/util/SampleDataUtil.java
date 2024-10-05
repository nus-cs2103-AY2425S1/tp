
package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
                new Person(new StudentId("12121212"), new Name("Alex Yeoh"),
                        new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        new Course("Geography"),
                        getTagSet("friends")),
                new Person(new StudentId("13131313"),
                        new Name("Bernice Yu"),
                        new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new Course("Computer Science"),
                        getTagSet("colleagues", "friends")),
                new Person(new StudentId("23461293"),
                        new Name("Charlotte Oliveiro"),
                        new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new Course("Data Science"),
                        getTagSet("neighbours")),
                new Person(new StudentId("98881999"),
                        new Name("David Li"),
                        new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new Course("Law"),
                        getTagSet("family")),
                new Person(new StudentId("87778888"),
                        new Name("Irfan Ibrahim"),
                        new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        new Course("Medicine"),
                        getTagSet("classmates")),
                new Person(new StudentId("98189818"),
                        new Name("Roy Balakrishnan"),
                        new Phone("92624417"),
                        new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        new Course("Nursing"),
                        getTagSet("colleagues"))
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

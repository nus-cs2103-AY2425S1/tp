package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"),
                    new Telegram("@alex"), getTagSet("friends"),
                    new Github("Alex")),
            new Person(new Name("Bernice Yu"), new Email("berniceyu@example.com"),
                new Telegram("@bernice"), getTagSet("colleagues", "friends"),
                    new Github("Bernice")),
            new Person(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"),
                    new Telegram("@charlotte"), getTagSet("neighbours"),
                    new Github("Charlotte")),
            new Person(new Name("David Li"), new Email("lidavid@example.com"),
                new Telegram("@david"), getTagSet("family"),
                    new Github("david")),
            new Person(new Name("Irfan Ibrahim"), new Email("irfan@example.com"),
                new Telegram("@irfan"), getTagSet("classmates"),
                    new Github("Irfan")),
            new Person(new Name("Roy Balakrishnan"), new Email("royb@example.com"),
                new Telegram("@roy"), getTagSet("colleagues"),
                    new Github("Roy"))
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

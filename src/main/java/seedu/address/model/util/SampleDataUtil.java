package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentClass;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Tags;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new StudentClass("19S13"), new Phone("87438807"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new StudentClass("6K"), new Phone("99272758"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new StudentClass("4A"), new Phone("93210283"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new StudentClass("14B"), new Phone("91031282"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new StudentClass("19D"), new Phone("92492021"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new StudentClass("5L"), new Phone("92624417"),
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
    public static Tags getTagSet(String... strings) {
        Set<Tag> tagSet = Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
        return new Tags(tagSet);
    }
}

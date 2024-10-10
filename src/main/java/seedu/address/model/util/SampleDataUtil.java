package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Nickname;
import seedu.address.model.person.StudentStatus;
import seedu.address.model.tag.Role;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Telegram("87438807"), new Email("alexyeoh@example.com"),
                       new StudentStatus("undergraduate 1"),
                getTagSet("President"), new Nickname("")),
            new Person(new Name("Bernice Yu"), new Telegram("99272758"), new Email("berniceyu@example.com"),
                       new StudentStatus("undergraduate 3"),
                getTagSet("President", "Admin"), new Nickname("<nn space>")),
            new Person(new Name("Charlotte Oliveiro"), new Telegram("93210283"), new Email("charlotte@example.com"),
                       new StudentStatus("masters"),
                getTagSet("Marketing"), new Nickname("")),
            new Person(new Name("David Li"), new Telegram("91031282"), new Email("lidavid@example.com"),
                       new StudentStatus("undergraduate 4"),
                getTagSet("Admin"), new Nickname("<nn space>")),
            new Person(new Name("Irfan Ibrahim"), new Telegram("92492021"), new Email("irfan@example.com"),
                       new StudentStatus("phd"),
                getTagSet("Events (internal)"), new Nickname("")),
            new Person(new Name("Roy Balakrishnan"), new Telegram("92624417"), new Email("royb@example.com"),
                       new StudentStatus("undergraduate 4"),
                getTagSet("External Relations"), new Nickname(""))
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
    public static Set<Role> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

}

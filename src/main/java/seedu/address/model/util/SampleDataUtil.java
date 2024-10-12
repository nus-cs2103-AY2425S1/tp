package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentStatus;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Nickname;
import seedu.address.model.tag.Role;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new TelegramHandle("87438807"), new Email("alexyeoh@example.com"),
                    new StudentStatus("undergraduate 1"),
                    getRoleSet("President"), new Nickname("")),
            new Person(new Name("Bernice Yu"), new TelegramHandle("99272758"), new Email("berniceyu@example.com"),
                    new StudentStatus("undergraduate 3"),
                    getRoleSet("President", "Admin"), new Nickname("<nn space>")),
            new Person(new Name("Charlotte Oliveiro"), new TelegramHandle("93210283"),
                    new Email("charlotte@example.com"),
                    new StudentStatus("masters"),
                    getRoleSet("Marketing"), new Nickname("")),
            new Person(new Name("David Li"), new TelegramHandle("91031282"), new Email("lidavid@example.com"),
                    new StudentStatus("undergraduate 4"),
                    getRoleSet("Admin"), new Nickname("<nn space>")),
            new Person(new Name("Irfan Ibrahim"), new TelegramHandle("92492021"), new Email("irfan@example.com"),
                    new StudentStatus("phd"),
                    getRoleSet("Events (internal)"), new Nickname("")),
            new Person(new Name("Roy Balakrishnan"), new TelegramHandle("92624417"), new Email("royb@example.com"),
                    new StudentStatus("undergraduate 4"),
                    getRoleSet("External Relations"), new Nickname(""))
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
    public static Set<Role> getRoleSet(String... strings) {
        return Arrays.stream(strings)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

}

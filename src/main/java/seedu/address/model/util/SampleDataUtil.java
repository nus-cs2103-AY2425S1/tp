package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("John Doe"), new Phone("98765432"), new Email("johnd@example.com"),
                    new Address("123 Main St"), EMPTY_REMARK,
                    getTagSet("friend")),
            new Person(new Name("Jane Smith"), new Phone("87654321"), new Email("janes@example.com"),
                    new Address("456 Elm St"), EMPTY_REMARK,
                    getTagSet("colleague")),
            new Person(new Name("Alice Johnson"), new Phone("86543210"), new Email("alicej@example.com"),
                    new Address("789 Oak St"), EMPTY_REMARK,
                    getTagSet("family")),
            new Person(new Name("Betsy"), new Phone("91231321"), new Email("betsy198@gmail.com"),
                    new Address("Woodlands Street 40"), new Remark("10 year relationship"),
                    getTagSet("friend"))
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

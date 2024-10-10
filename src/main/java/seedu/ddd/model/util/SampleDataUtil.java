package seedu.ddd.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ddd.model.AddressBook;
import seedu.ddd.model.ReadOnlyAddressBook;
import seedu.ddd.model.person.Address;
import seedu.ddd.model.person.Client;
import seedu.ddd.model.person.Contact;
import seedu.ddd.model.person.Date;
import seedu.ddd.model.person.Email;
import seedu.ddd.model.person.Name;
import seedu.ddd.model.person.Phone;
import seedu.ddd.model.person.Service;
import seedu.ddd.model.person.Vendor;
import seedu.ddd.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Client(new Name("A"), new Phone("12345678"), new Email("a@a.com"), new Address("A"),
                new Date("01 Jan 2000"),
                getTagSet("another")),
            new Vendor(new Name("B"), new Phone("12345678"), new Email("b@b.com"), new Address("B"),
                new Service("Catering"),
                getTagSet("test")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
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

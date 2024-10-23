package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rsvp;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Vendor(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), new Company("Company 1"), new Budget(5000.00)),
            new Vendor(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), new Company("Company 2"), new Budget(7500.50)),
            new Vendor(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), new Company("Company 3"), new Budget(10000.00))
        };
    }

    public static Person[] getSampleGuests() {
        return new Person[]{
            new Guest(new Name("Alex Yeoh"), new Phone("83238717"), new Email("alexyeoh@example.com"),
                new Address("Blk 50 Geylang Street 31, #03-40"),
                getTagSet("friends"), new Rsvp("Pending")),
            new Guest(new Name("Brian Smith"), new Phone("99311258"), new Email("brainsmith@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #10-20"),
                getTagSet("colleagues", "friends"), new Rsvp("Accepted")),
            new Guest(new Name("Catherine Lee"), new Phone("93212323"), new Email("catherine@example.com"),
                new Address("Blk 131 Ang Mo Kio Street 77, #01-04"),
                getTagSet("neighbours"), new Rsvp("Declined")),
            new Guest(new Name("David Nguyen"), new Phone("87134653"), new Email("nguyen@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new Rsvp("Pending")),
            new Guest(new Name("Mohammad Ismal"), new Phone("90783445"), new Email("ismal@example.com"),
                new Address("Blk 47 Tampines Street 20, #20-35"),
                getTagSet("classmates"), new Rsvp("Accepted")),
            new Guest(new Name("Tony Stark"), new Phone("83129340"), new Email("stark@example.com"),
                new Address("Blk 50 Aljunied Street 91, #05-31"),
                getTagSet("colleagues"), new Rsvp("Pending"))
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

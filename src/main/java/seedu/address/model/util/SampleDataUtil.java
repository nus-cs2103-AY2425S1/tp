package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Listing;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.UniqueListingList;
import seedu.address.model.tag.PersonTag;
import seedu.address.model.tag.PersonTagType;
import seedu.address.model.tag.PropertyTag;
import seedu.address.model.tag.PropertyTagType;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("tenant"), new Remark("A Remark"),
                getListingsList(new String[]{"hdb", "123 NUS street"})
            ),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("buyer"), new Remark("A Remark"),
                getListingsList(new String[]{"condo", "123 NTU street"})),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("seller"), new Remark("A Remark"),
                getListingsList(new String[][]{
                        {"landed", "123 SMU street"},
                        {"hdb", "1245 SUTD street"},
                })),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("landlord"), new Remark("A Remark"),
                new UniqueListingList()),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("buyer"), new Remark("A Remark"), new UniqueListingList()),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("seller"), new Remark("A Remark"), new UniqueListingList())
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
                .map(tagName -> {
                    if (PersonTagType.isValidPersonTag(tagName)) {
                        return new PersonTag(tagName);
                    } else if (PropertyTagType.isValidPropertyTag(tagName)) {
                        return new PropertyTag(tagName);
                    } else {
                        return new Tag(tagName);
                    }
                })
                .collect(Collectors.toSet());
    }


    /**
     * Returns an arrayList of Listing
     */
    public static UniqueListingList getListingsList(String[]... strings) {
        UniqueListingList newListings = new UniqueListingList();

        Arrays.stream(strings)
                .forEach(listingStrInfo -> {
                    newListings.add(
                           new Listing(
                                   PropertyTagType.fromString(listingStrInfo[0]),
                                   new Address(listingStrInfo[1])
                           )
                    );
                });

        return newListings;
    }


}

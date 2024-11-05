package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfLastVisit;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                    Optional.of(new Email("alexyeoh@example.com")),
                    Optional.of(new Address("Blk 30 Geylang Street 29, #06-40")), getTagSet("elderly"),
                    Optional.of(new DateOfLastVisit("01-01-2024")),
                    Optional.of(new EmergencyContact("91234567")),
                    new Remark("")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    Optional.of(new Email("berniceyu@example.com")),
                    Optional.of(new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
                    getTagSet("single mother", "2 kids"), Optional.of(new DateOfLastVisit("02-02-2024")),
                    Optional.of(new EmergencyContact("91234567")),
                    new Remark("Follow up on children's education progress")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    Optional.of(new Email("charlotte@example.com")),
                    Optional.of(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                    getTagSet("low income"), Optional.of(new DateOfLastVisit("03-03-2024")),
                    Optional.of(new EmergencyContact("91234567")),
                    new Remark("")),
            new Person(new Name("David Li"), new Phone("91031282"),
                    Optional.of(new Email("lidavid@example.com")),
                    Optional.of(new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
                    getTagSet("disability"), Optional.of(new DateOfLastVisit("04-04-2024")),
                    Optional.of(new EmergencyContact("98765432")),
                    new Remark("Look for job opportunities")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    Optional.of(new Email("irfan@example.com")),
                    Optional.of(new Address("Blk 47 Tampines Street 20, #17-35")),
                    getTagSet("ex-convict"), Optional.of(new DateOfLastVisit("05-05-2024")),
                    Optional.of(new EmergencyContact("91234567")),
                    new Remark("Violent tendencies")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    Optional.of(new Email("royb@example.com")),
                    Optional.of(new Address("Blk 45 Aljunied Street 85, #11-31")),
                    getTagSet("elderly"), Optional.of(new DateOfLastVisit("06-06-2024")),
                    Optional.of(new EmergencyContact("92492021")), new Remark(""))};
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
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

}

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
import seedu.address.model.person.company.Company;
import seedu.address.model.person.company.Industry;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Student(new Name("Alex Yeoh"), new StudentId("A1234567B"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("year1")),
            new Company(new Name("Apple"), new Industry("Technology"), new Phone("99272758"),
                    new Email("contact@apple.com"),
                    new Address("1 Infinite Loop, Cupertino, CA"),
                    getTagSet("partner", "technology")),
            new Student(new Name("Charlotte Oliveiro"), new StudentId("A2345678C"),
                    new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("paidFee")),
            new Company(new Name("National University of Singapore"), new Industry("Education"), new Phone("91031282"),
                    new Email("info@nus.edu.sg"),
                    new Address("21 Lower Kent Ridge Rd, Singapore"),
                    getTagSet("education", "research")),
            new Student(new Name("Irfan Ibrahim"), new StudentId("A3456789D"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("year2")),
            new Company(new Name("Tesla Motors"), new Industry("Automotive"), new Phone("92624417"),
                    new Email("contact@tesla.com"),
                    new Address("3500 Deer Creek Road, Palo Alto, CA"),
                    getTagSet("innovation"))
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

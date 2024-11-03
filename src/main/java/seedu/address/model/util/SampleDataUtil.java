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
import seedu.address.model.person.Student;
import seedu.address.model.tag.Education;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Grade EMPTY_GRADE = new Grade("0");
    public static final Education DEFAULT_EDUCATION = new Education("Primary");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), DEFAULT_EDUCATION, EMPTY_GRADE,
                    new Name("Alex dad"), new Phone("91111111"), new Email("alexdad@example.com"),
                    getTagSet("hyperactive")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), DEFAULT_EDUCATION, EMPTY_GRADE,
                    new Name("Bernice dad"), new Phone("92222222"), new Email("bernicedad@example.com"),
                    getTagSet("dyslexic")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), DEFAULT_EDUCATION, EMPTY_GRADE,
                    new Name("Charlotte mom"), new Phone("93333333"), new Email("charlottemom@example.com"),
                    getTagSet("ambitious")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), DEFAULT_EDUCATION, EMPTY_GRADE,
                    new Name("David mom"), new Phone("94444444"), new Email("davidmom@example.com"),
                    getTagSet("adaptable")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), DEFAULT_EDUCATION, EMPTY_GRADE,
                    new Name("Irfan mom"), new Phone("95555555"), new Email("irfanmom@example.com"),
                    getTagSet("adept")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), DEFAULT_EDUCATION, EMPTY_GRADE,
                    new Name("Roy mom"), new Phone("96666666"), new Email("roydad@example.com"),
                    getTagSet("determined"))
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

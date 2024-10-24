package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.University;
import seedu.address.model.person.WorkExp;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new WorkExp("Intern,Google,2022"),
                getTagSet("friends"), new University("NUS"), new Major("Business"),
                    getInterestSet("reading", "cycling")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new WorkExp("Engineer,Microsoft,2023"),
                getTagSet("colleagues", "friends"), new University("NTU"), new Major("Engineering"),
                    getInterestSet("swimming")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new WorkExp("Manager,Amazon,2024"),
                getTagSet("neighbours"), new University("SMU"), new Major("Accounting"),
                    getInterestSet("hiking")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new WorkExp("Director,Google,2023"),
                getTagSet("family"), new University("NUS"), new Major("Law"),
                    getInterestSet("traveling", "photography")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new WorkExp("Intern,Microsoft,2023"),
                getTagSet("classmates"), new University("SUTD"), new Major("Design"),
                    getInterestSet("gaming")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new WorkExp("Intern,Amazon,2024"),
                getTagSet("colleagues"), new University("NUS"), new Major("Computer Science"),
                    getInterestSet("programming", "chess"))
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
    /**
     * Returns an interest set containing the list of strings given.
     */
    public static Set<Interest> getInterestSet(String... strings) {
        return Arrays.stream(strings)
                .map(Interest::new)
                .collect(Collectors.toSet());
    }

}

package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.LogList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Triage;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Nric("S1234567A"), new Address("Blk 30 Geylang Street 29', #06-40"), new Triage("1"),
                    new Remark("Drug Allergy"), getTagSet("Diabetic"),
                    new Appointment("31-12-2024 00:00"), new LogList()),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Nric("S1234567B"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Triage("1"), EMPTY_REMARK,
                getTagSet("G6PD", "COPD"), new Appointment("30-12-2024 00:00"), new LogList()),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Nric("S1234567C"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Triage("1"), EMPTY_REMARK,
                getTagSet("Diabetic", "G6PD"), new Appointment("01-05-2025 13:22"), new LogList()),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Nric("S1234567D"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Triage("1"), EMPTY_REMARK,
                getTagSet("Alzheimers"), new Appointment("29-10-2024 23:59"), new LogList()),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Nric("S0123456Z"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Triage("1"), EMPTY_REMARK,
                getTagSet("ALS"), null, new LogList()),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Nric("S1234567E"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Triage("1"), EMPTY_REMARK,
                getTagSet("Alzheimers", "Parkinsons"), null, new LogList())
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

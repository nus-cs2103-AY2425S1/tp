package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getSubjectSet;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

public class SampleDataUtilTest {
    private final Person[] samples = new Person[] {
        new Tutor(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40, 999999"), new Hours("69"),
                getSubjectSet("english")),
        new Tutor(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18, 999999"), new Hours("69"),
                getSubjectSet("english")),
        new Tutor(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04, 999999"), new Hours("69"),
                getSubjectSet("english")),
        new Tutor(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43, 999999"), new Hours("69"),
                getSubjectSet("english")),
        new Tutee(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35, 999999"), new Hours("69"),
                getSubjectSet("english")),
        new Tutee(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31, 999999"), new Hours("69"), getSubjectSet("english"))
    };

    private final AddressBook sampleAddressbook = new AddressBook();

    @Test
    public void generateSampleData() {
        int i = 0;
        for (Person p : SampleDataUtil.getSamplePersons()) {
            assertEquals(p, samples[i]);
            i++;
        }
    }

    @Test
    public void generateSampleAddressBook() {
        for (Person p : samples) {
            sampleAddressbook.addPerson(p);
        }
        assertEquals(sampleAddressbook, SampleDataUtil.getSampleAddressBook());
    }

    @Test
    public void generateSubjectSet() {
        assertEquals(Set.of(new Subject("english")), SampleDataUtil.getSubjectSet("english"));
    }
}

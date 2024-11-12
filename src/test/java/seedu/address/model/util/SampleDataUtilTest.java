package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyScheduleList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    public static final Person[] SAMPLE_PERSONS = new Person[] {
        new Person(new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@example.com"),
            new Address("Blk 30 Geylang Street 29, #06-40"),
            Set.of(new Tag("friends"))),
        new Person(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"),
            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            Set.of(new Tag("colleagues"), new Tag("friends"))),
        new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
            new Email("charlotte@example.com"),
            new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            Set.of(new Tag("neighbours"))),
        new Person(new Name("David Li"), new Phone("91031282"),
            new Email("lidavid@example.com"),
            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            Set.of(new Tag("family"))),
        new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
            new Email("irfan@example.com"),
            new Address("Blk 47 Tampines Street 20, #17-35"),
            Set.of(new Tag("classmates"))),
        new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
            new Email("royb@example.com"),
            new Address("Blk 45 Aljunied Street 85, #11-31"),
            Set.of(new Tag("colleagues")))
        };

    @Test
    public void getSamplePersons_equals() {
        Person[] sampleDataUtilSamplePersons = SampleDataUtil.getSamplePersons();
        for (int i = 0; i < SAMPLE_PERSONS.length; i++) {
            assertTrue(sampleDataUtilSamplePersons[i].equals(SAMPLE_PERSONS[i]));
        }
    }

    @Test
    public void getSampleAddressBook_equals() {
        ObservableList<Person> sampleAddressBook = SampleDataUtil
                .getSampleAddressBook().getPersonList();
        for (int i = 0; i < SAMPLE_PERSONS.length; i++) {
            assertTrue(sampleAddressBook.get(i).equals(SAMPLE_PERSONS[i]));
        }
        assertTrue(SampleDataUtil.getWasSampleAddressBookGenerated());
    }

    @Test
    public void getSampleMeetings_assertNonEmptyAddressbook() {
        assertThrows(AssertionError.class, () -> SampleDataUtil
                .getSampleMeetings(new AddressBook()));
    }

    @Test
    public void getSampleScheduleList_equals() {
        ReadOnlyScheduleList sampleScheduleList = SampleDataUtil
                .getSampleScheduleList(SampleDataUtil.getSampleAddressBook());
        assertTrue(sampleScheduleList.getMeetingList().size() == 7);
    }
}

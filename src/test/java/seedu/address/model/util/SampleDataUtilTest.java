package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;


public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsCorrectNumberOfPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(6, samplePersons.length);
    }

    @Test
    public void getSamplePersons_returnsValidPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        for (Person person : samplePersons) {
            assertNotNull(person.getName());
            assertNotNull(person.getPhone());
            assertNotNull(person.getEmail());
            assertNotNull(person.getAddress());
            assertNotNull(person.getTags());
        }
    }

    @Test
    public void getSamplePersons_containsExpectedPerson() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        Person expectedPerson = new Person(
                new Name("Alex Yeoh"),
                new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                SampleDataUtil.getTagSet("friends"),
                "Annual life insurance premium: $1,200",
                "alex_yeoh"
        );
        assertEquals(expectedPerson, samplePersons[0]);
    }
}

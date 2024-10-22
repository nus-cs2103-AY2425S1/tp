package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleDataUtilTest {
    @Test
    public void getSamplePersons_validData_personsArray() {
        // Test that sample persons are correctly created
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assert(samplePersons.length > 0); // Check the array size if it is more than 0
    }

    @Test
    public void getSamplePersons_validFields_personsDataValid() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);

        for (Person person : samplePersons) {
            assert(!person.getName().fullName.isEmpty());
            assert(person.getEmail().value.contains("@")); // Basic email validation
            assert(person.getTelegram().value.matches("@[a-zA-Z0-9]+")); // Basic telegram validation
        }
    }
}

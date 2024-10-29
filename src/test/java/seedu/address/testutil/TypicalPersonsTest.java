package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getSamplePersons;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class TypicalPersonsTest {

    @Test
    public void samplePersonsData_correctPersonCount() {
        Person[] samplePersons = getSamplePersons();

        // Check that there are exactly 6 sample persons
        assertEquals(6, samplePersons.length, "The sample data should contain exactly 6 persons.");
    }

    @Test
    public void samplePersonsData_correctDetails() {
        Person[] samplePersons = getSamplePersons();

        // Validate the details of one of the sample persons
        Person firstPerson = samplePersons[0];
        assertEquals("Alex Yeoh", firstPerson.getName().fullName);
        assertEquals("87438807", firstPerson.getPhone().value);
        assertEquals("alexyeoh@example.com", firstPerson.getEmail().value);
        assertEquals("Blk 30 Geylang Street 29, #06-40", firstPerson.getAddress().value);
        assertEquals("NUS", firstPerson.getUniversity().value);
        assertEquals("Business", firstPerson.getMajor().value);
    }
}

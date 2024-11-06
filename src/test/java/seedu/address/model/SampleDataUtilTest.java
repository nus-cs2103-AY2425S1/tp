package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Allergy;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleDataUtilTest {

    @Test
    public void testGetSamplePersons() {
        // Get the sample persons from SampleDataUtil
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Check the first person: Alex Yeoh
        Person person1 = samplePersons[0];
        assertEquals("Alex Yeoh", person1.getName().toString());
        assertEquals("87438807", person1.getPhone().toString());
        assertEquals("alexyeoh@example.com", person1.getEmail().toString());
        assertEquals("Blk 30 Geylang Street 29, #06-40", person1.getAddress().toString());
        assertEquals("High Risk", person1.getTag().toString());
        assertTrue(person1.getAllergies().contains(new Allergy("None")),
                "Allergy set should contain 'None' for Alex Yeoh.");
        assertEquals(SampleDataUtil.EMPTY_DATE, person1.getDate());

        // Check the second person: Bernice Yu
        Person person2 = samplePersons[1];
        assertEquals("Bernice Yu", person2.getName().toString());
        assertEquals("99272758", person2.getPhone().toString());
        assertEquals("berniceyu@example.com", person2.getEmail().toString());
        assertEquals("Blk 30 Lorong 3 Serangoon Gardens, #07-18", person2.getAddress().toString());
        assertEquals("Low Risk", person2.getTag().toString());
        assertEquals(Set.of(new Allergy("Peanuts")), person2.getAllergies()); // Peanuts allergy
    }
}

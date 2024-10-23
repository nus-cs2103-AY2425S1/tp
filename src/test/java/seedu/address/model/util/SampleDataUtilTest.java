package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_notNull() {
        assertNotNull(SampleDataUtil.getSamplePersons());
    }

    @Test
    public void getSamplePersons_correctData() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        Person firstPerson = samplePersons[0];
        assertEquals("Alex Yeoh", firstPerson.getName().fullName);
        assertEquals("87438807", firstPerson.getPhone().value);
        assertEquals("alexyeoh@example.com", firstPerson.getEmail().value);
        assertEquals("Blk 30 Geylang Street 29, #06-40", firstPerson.getAddress().value);
        assertEquals(1, firstPerson.getPriorityLevel().getValue());
    }

    @Test
    public void getSampleAddressBook_containsExpectedPersons() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAb);
        assertEquals(6, sampleAb.getPersonList().size()); // assuming there are 6 sample persons

        Person firstPerson = sampleAb.getPersonList().get(0);
        assertEquals("Alex Yeoh", firstPerson.getName().fullName);
        assertEquals("87438807", firstPerson.getPhone().value);
        assertEquals("alexyeoh@example.com", firstPerson.getEmail().value);
        assertEquals("Blk 30 Geylang Street 29, #06-40", firstPerson.getAddress().value);
        assertEquals(1, firstPerson.getPriorityLevel().getValue());
    }

    @Test
    public void getTagSet_correctSetSizeAndContents() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "colleagues");
        assertEquals(2, tags.size());
        assertTrue(tags.contains(new Tag("friends")));
        assertTrue(tags.contains(new Tag("colleagues")));
    }
}

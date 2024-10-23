package tahub.contacts.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;

import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void testGetSamplePersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        assertNotNull(samplePersons, "Sample persons should not be null");
    }

    @Test
    public void testGetSampleAddressBook() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        assertNotNull(sampleAddressBook, "Sample address book should not be null");
    }

    @Test
    public void testGetTagSet() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends", "colleagues");

        assertNotNull(tagSet, "Tag set should not be null");
        assertEquals(2, tagSet.size(), "Tag set should contain 2 tags");
    }
}

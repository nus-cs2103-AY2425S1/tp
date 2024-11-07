package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyWeddingBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Wedding;

public class SampleDataUtilTest {

    @Test
    public void test_getSamplePersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assertEquals(11, samplePersons.length);
    }

    @Test
    public void test_getSampleAddressBook() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAddressBook);
        assertEquals(11, sampleAddressBook.getPersonList().size());
    }

    @Test
    public void test_getTagSet() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("Anthony & Mariah", "Vijay s/o Ramesh & Priya d/o Rahul");
        assertNotNull(tagSet);
        assertEquals(2, tagSet.size());
    }

    @Test
    public void test_getParticipantSet() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        Set<Person> participantSet = SampleDataUtil.getParticipantSet(samplePersons);
        assertNotNull(participantSet);
        assertEquals(11, participantSet.size());
    }

    @Test
    public void test_getSampleWeddings() {
        Wedding[] sampleWeddings = SampleDataUtil.getSampleWeddings();
        assertNotNull(sampleWeddings);
        assertEquals(6, sampleWeddings.length);
    }

    @Test
    public void test_getSampleWeddingBook() {
        ReadOnlyWeddingBook sampleWeddingBook = SampleDataUtil.getSampleWeddingBook();
        assertNotNull(sampleWeddingBook);
        assertEquals(6, sampleWeddingBook.getWeddingList().size());
    }
}

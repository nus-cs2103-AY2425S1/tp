package seedu.address.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

class SampleDataUtilTest {

    @Test
    void getSamplePersons_correctNumberOfPersons_success() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(6, samplePersons.length);
        assertNotEquals(5, samplePersons.length);
    }

    @Test
    void getSamplePersons_correctDetails_success() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        Person alex = samplePersons[0];
        assertEquals("Alex Yeoh", alex.getName().fullName);
        assertEquals("87438807", alex.getPhone().value);
        assertEquals("alexyeoh@example.com", alex.getEmail().value);
        assertEquals("Blk 30 Geylang Street 29, #06-40", alex.getAddress().value);
        assertFalse(Integer.parseInt(alex.getPayment().overdueAmount) < 0);
        // assertEquals(alex.getParticipation().attendanceDate,
        // LocalDate.parse("08/10/2024", Attendance.VALID_DATE_FORMAT));
        assertTrue(alex.getTags().contains(new Tag("friends")));
    }

    @Test
    void getSampleAddressBook_correctNumberOfPersons_success() {
        // Verify that the sample AddressBook contains all the sample persons
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        assertEquals(6, sampleAddressBook.getPersonList().size());
    }

    @Test
    void getSampleAddressBook_correctPersonDetails_success() {
        // Verify that the details of the first person in the AddressBook are correct
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        Person bernice = sampleAddressBook.getPersonList().get(1);

        assertEquals("Bernice Yu", bernice.getName().fullName);
        assertEquals("99272758", bernice.getPhone().value);
        assertEquals("berniceyu@example.com", bernice.getEmail().value);
    }

    @Test
    void getTagSet_correctConversion_success() {
        // Verify that getTagSet correctly converts strings to Tag objects
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "colleagues");

        assertEquals(2, tags.size());
        assertTrue(tags.contains(new Tag("friends")));
        assertTrue(tags.contains(new Tag("colleagues")));
    }
}


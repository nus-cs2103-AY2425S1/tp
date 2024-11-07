package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

public class SampleDataUtilTest {

    @Test
    public void getSampleAddressBook_validData_success() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        assertEquals(6, sampleAddressBook.getPersonList().size());

        String[] expectedNames = {"Alex Yeoh", "Bernice Yu", "Charlotte Oliveiro", "David Li",
                "Irfan Ibrahim", "Roy Balakrishnan"};
        String[] expectedPhones = {"87438807", "99272758", "93210283", "91031282", "92492021", "92624417"};
        String[] expectedEmails = {"alexyeoh@example.com", "berniceyu@example.com", "charlotte@example.com",
                "lidavid@example.com", "irfan@example.com", "royb@example.com"};
        String[] expectedAddresses = {"Blk 30 Geylang Street 29, #06-40, 999999",
                "Blk 30 Lorong 3 Serangoon Gardens, #07-18, 999999",
                "Blk 11 Ang Mo Kio Street 74, #11-04, 999999",
                "Blk 436 Serangoon Gardens Street 26, #16-43, 999999",
                "Blk 47 Tampines Street 20, #17-35, 999999",
                "Blk 45 Aljunied Street 85, #11-31, 999999"
        };
        String[] expectedHours = {"69", "69", "69", "69", "69", "69"};
        String[] expectedSubjects = {"english", "english", "english", "english", "english", "english"};

        for (int i = 0; i < sampleAddressBook.getPersonList().size(); i++) {
            Person person = sampleAddressBook.getPersonList().get(i);
            if (i < 4) {
                assertTrue(person instanceof Tutor);
            } else {
                assertTrue(person instanceof Tutee);
            }
            assertEquals(expectedNames[i], person.getName().toString());
            assertEquals(expectedPhones[i], person.getPhone().toString());
            assertEquals(expectedEmails[i], person.getEmail().toString());
            assertEquals(expectedAddresses[i], person.getAddress().toString());
            assertEquals(expectedHours[i], person.getHours().toString());
            assertTrue(person.getSubjects().contains(new Subject(expectedSubjects[i])));
        }
    }

    @Test
    public void getSamplePersons_validData_success() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(6, samplePersons.length);

        String[] expectedNames = {"Alex Yeoh", "Bernice Yu", "Charlotte Oliveiro",
                "David Li", "Irfan Ibrahim", "Roy Balakrishnan"};
        String[] expectedPhones = {"87438807", "99272758", "93210283", "91031282", "92492021", "92624417"};
        String[] expectedEmails = {"alexyeoh@example.com", "berniceyu@example.com",
                "charlotte@example.com", "lidavid@example.com", "irfan@example.com", "royb@example.com"};
        String[] expectedAddresses = {
                "Blk 30 Geylang Street 29, #06-40, 999999",
                "Blk 30 Lorong 3 Serangoon Gardens, #07-18, 999999",
                "Blk 11 Ang Mo Kio Street 74, #11-04, 999999",
                "Blk 436 Serangoon Gardens Street 26, #16-43, 999999",
                "Blk 47 Tampines Street 20, #17-35, 999999",
                "Blk 45 Aljunied Street 85, #11-31, 999999"
        };
        String[] expectedHours = {"69", "69", "69", "69", "69", "69"};
        String[] expectedSubjects = {"english", "english", "english", "english", "english", "english"};

        for (int i = 0; i < samplePersons.length; i++) {
            Person person = samplePersons[i];
            if (i < 4) {
                assertTrue(person instanceof Tutor);
            } else {
                assertTrue(person instanceof Tutee);
            }
            assertEquals(expectedNames[i], person.getName().toString());
            assertEquals(expectedPhones[i], person.getPhone().toString());
            assertEquals(expectedEmails[i], person.getEmail().toString());
            assertEquals(expectedAddresses[i], person.getAddress().toString());
            assertEquals(expectedHours[i], person.getHours().toString());
            assertTrue(person.getSubjects().contains(new Subject(expectedSubjects[i])));
        }
    }

    @Test
    public void getSubjectSet_validData_success() {
        Set<Subject> subjects = SampleDataUtil.getSubjectSet("Math", "Science");
        assertEquals(2, subjects.size());
        assertTrue(subjects.contains(new Subject("Math")));
        assertTrue(subjects.contains(new Subject("Science")));
    }

    @Test
    public void getSubjectSet_emptyData_emptySet() {
        Set<Subject> subjects = SampleDataUtil.getSubjectSet();
        assertTrue(subjects.isEmpty());
    }
}
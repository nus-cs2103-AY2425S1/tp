package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleDataUtilTest {
    @Test
    public void getSamplePersons_validData_personsArray() {
        // Test that sample persons are correctly created
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assertEquals(6, samplePersons.length); // Check the array size
    }

    @Test
    public void getSampleAddressBook_validData_addressBook() {
        // Test that the sample address book contains the expected sample persons
        AddressBook sampleAddressBook = (AddressBook) SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAddressBook);
        assertEquals(6, sampleAddressBook.getPersonList().size()); // Check the number of persons
    }
}

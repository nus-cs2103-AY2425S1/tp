package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class AddressBookBuilderTest {

    @Test
    public void sampleAddressBook_containsSamplePersons() {
        ReadOnlyAddressBook sampleAddressBook = getSampleAddressBook();

        // Check that the address book contains exactly 6 persons
        assertEquals(6, sampleAddressBook.getPersonList().size());

        // Check that the first sample person is in the address book
        Person firstPerson = sampleAddressBook.getPersonList().get(0);
        assertEquals("Alex Yeoh", firstPerson.getName().fullName);
    }
}

package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleAddressBookTest {

    private final ReadOnlyAddressBook addressBook = new SampleDataUtil().getSampleAddressBook();

    @Test
    public void constructor() {
        Person[] expectedPersons = new SampleDataUtil().getSamplePersons();
        List<Person> persons = addressBook.getPersonList();
        assertEquals(persons.size(), expectedPersons.length);
        for (int i = 0; i < persons.size(); i++) {
            assertEquals(expectedPersons[i], persons.get(i));
        }
    }
}

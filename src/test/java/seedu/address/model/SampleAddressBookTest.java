package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Event;
import seedu.address.model.id.counter.list.IdCounterList;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleAddressBookTest {

    private final ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();

    @Test
    public void constructor() {
        Person[] expectedPersons = SampleDataUtil.getSamplePersons();
        Event[] expectedEvents = SampleDataUtil.getSampleEvents();
        IdCounterList expectedIdCounterList = SampleDataUtil.getSampleIdCounterList();
        List<Person> persons = addressBook.getPersonList();
        List<Event> events = addressBook.getEventList();
        IdCounterList idCounterList = addressBook.getIdCounterList();
        assertEquals(persons.size(), expectedPersons.length);
        assertEquals(events.size(), expectedEvents.length);
        assertEquals(expectedIdCounterList, idCounterList);
        for (int i = 0; i < persons.size(); i++) {
            assertEquals(expectedPersons[i], persons.get(i));
        }
        for (int i = 0; i < events.size(); i++) {
            assertEquals(expectedEvents[i], events.get(i));
        }
    }
}

package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.id.counter.list.IdCounterList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), getEventIdSet(), 1),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getEventIdSet(1), 2),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getEventIdSet(1, 2), 3),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getEventIdSet(2), 4),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getEventIdSet(1), 5),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getEventIdSet(1), 6)
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new EventName("CS2103T Project Meeting"), new EventDescription("Project Meeting for CS2103T"),
                new EventDuration(LocalDate.parse("2024-10-05"), LocalDate.parse("2024-10-05")), 1),
            new Event(new EventName("Orbital Workshop"), new EventDescription("React Workshop for Orbital"),
                new EventDuration(LocalDate.parse("2024-10-06"), LocalDate.parse("2024-10-06")), 2)
        };
    }

    public static IdCounterList getSampleIdCounterList() {
        Event[] events = getSampleEvents();
        Person[] persons = getSamplePersons();
        return new IdCounterList(persons[persons.length - 1].getId(), events[events.length - 1].getEventId());
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        int largestPersonId = 0;
        int largestEventId = 0;
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
            if (samplePerson.getId() > largestPersonId) {
                largestPersonId = samplePerson.getId();
            }
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
            if (sampleEvent.getEventId() > largestEventId) {
                largestEventId = sampleEvent.getEventId();
            }
        }
        IdCounterList idCounterList = new IdCounterList(largestPersonId, largestEventId);
        sampleAb.setIdCounterList(idCounterList);
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a set containing the list of event IDs given.
     *
     * @param ids The event IDs to be added to the set.
     * @return A set containing the event IDs.
     */
    public static Set<Integer> getEventIdSet(Integer... ids) {
        return Arrays.stream(ids)
                .collect(Collectors.toSet());
    }
}

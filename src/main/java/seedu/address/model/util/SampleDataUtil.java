package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final int ADDRESSBOOK_SIZE = 6; // number of Persons in sample address book

    public static final Person PERSON_ALEX =
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Relationship("Father"));
    public static final Person PERSON_BERNICE =
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Relationship("Mother"));
    public static final Person PERSON_CHARLOTTE =
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Relationship("Niece"));
    public static final Person PERSON_DAVID =
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Relationship("Grandfather"));
    public static final Person PERSON_IRFAN =
                    new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Relationship("Uncle"));
    public static final Person PERSON_ROY =
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Relationship("Cousin"));

    public static Person[] getSamplePersons() {
        return new Person[] { PERSON_ALEX,
                              PERSON_BERNICE,
                              PERSON_CHARLOTTE,
                              PERSON_DAVID,
                              PERSON_IRFAN,
                              PERSON_ROY };
    }

    public static Set<Person> getSampleAttendees() {
        Set<Person> attendees = new HashSet<>();
        attendees.addAll(Arrays.asList(getSamplePersons()));
        return attendees;
    }

    public static Set<Index> getSampleIndexes() {
        Set<Index> indexes = new HashSet<>();
        for (int i = 0; i < ADDRESSBOOK_SIZE; i++) {
            indexes.add(Index.fromZeroBased(i));
        }
        return indexes;
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static Event[] getSampleEvents() {

        Set<Person> sampleAttendees = new HashSet<>(Arrays.asList(getSamplePersons()));
        return new Event[] {
            new Event("Emily's Birthday Party", LocalDate.of(2024, 10, 1), new Address("123 Party Lane"),
                    sampleAttendees),
            new Event("Family Reunion", LocalDate.of(2024, 10, 5), new Address("456 Family St"), sampleAttendees),
            new Event("Grandpa's Wedding Day", LocalDate.of(2024, 10, 10), new Address("789 Wedding Blvd"),
                    sampleAttendees)
        };
    }

    public static ReadOnlyEventBook getSampleEventBook() {
        EventBook sampleEb = new EventBook();
        for (Event sampleEvent : getSampleEvents()) {
            sampleEb.addEvent(sampleEvent);
        }
        return sampleEb;
    }


    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

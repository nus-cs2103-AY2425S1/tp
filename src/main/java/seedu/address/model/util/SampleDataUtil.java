package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.ReadOnlyEventManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                 new TelegramUsername("alexyeoh"),
                getRoleSet("attendee")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                 new TelegramUsername("berniceyu"),
                getRoleSet("volunteer", "vendor")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new TelegramUsername("charlotte"),
                getRoleSet("sponsor")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new TelegramUsername("davidli"),
                getRoleSet("attendee", "sponsor", "vendor")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new TelegramUsername("irfan23"),
                getRoleSet("attendee")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                 new TelegramUsername("roy02"),
                getRoleSet("sponsor"))

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a sample list of {@code Event} objects.
     */
    public static Event[] getSampleEvents() {
        Person[] samplePersons = getSamplePersons(); // Reuse sample persons for the event participants

        return new Event[] {
            new Event("NUS Student Life Fair 2024",
                    getPersonSet(samplePersons[0], samplePersons[1]),
                    getPersonSet(samplePersons[2]),
                    getPersonSet(samplePersons[3]),
                    getPersonSet(samplePersons[4])
            ),
            new Event("NUS Open House 2024",
                    getPersonSet(samplePersons[1]),
                    getPersonSet(samplePersons[0], samplePersons[2]),
                    getPersonSet(samplePersons[3]),
                    getPersonSet(samplePersons[4], samplePersons[5])
            )
        };
    }

    /**
     * Helper method that returns a set of persons.
     */
    public static Set<Person> getPersonSet(Person... persons) {
        Set<Person> personSet = new HashSet<>();
        for (Person person : persons) {
            personSet.add(person);
        }
        return personSet;
    }

    /**
     * Returns a sample {@code EventManager} populated with sample events.
     */
    public static ReadOnlyEventManager getSampleEventManager() {
        EventManager sampleEventManager = new EventManager();
        for (Event sampleEvent : getSampleEvents()) {
            sampleEventManager.addEvent(sampleEvent);
        }
        return sampleEventManager;
    }

    /**
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        RoleHandler rh = new RoleHandler();
        return Arrays.stream(strings)
                .map(string -> tryGetRole(rh, string)) // Delegate exception handling to tryGetRole
                .flatMap(Optional::stream) // Flatten the Optional into a stream, skipping empty ones
                .collect(Collectors.toSet());
    }

    /**
     * Helper method that attempts to get a role and returns an Optional.
     */
    private static Optional<Role> tryGetRole(RoleHandler rh, String string) {
        try {
            return Optional.of(rh.getRole(string));
        } catch (InvalidRoleException e) {
            return Optional.empty();
        }
    }

}

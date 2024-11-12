package seedu.eventfulnus.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.util.Pair;
import seedu.eventfulnus.logic.parser.ParserUtil;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.Faculty;
import seedu.eventfulnus.model.ReadOnlyAddressBook;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Email;
import seedu.eventfulnus.model.person.Name;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.Phone;
import seedu.eventfulnus.model.person.role.Role;
import seedu.eventfulnus.model.person.role.Sport;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alice Pauline"), new Phone("94351253"), new Email("alice@example.com"),
                getRoleSet("Athlete - COM - Volleyball Women, Floorball Women")),
            new Person(new Name("Benson Meier"), new Phone("98765432"), new Email("johnd@example.com"),
                getRoleSet("Volunteer - Emcee", "Athlete - DEN - Swimming Men")),
            new Person(new Name("Carl Kurz"), new Phone("95352563"), new Email("heinz@example.com"),
                getRoleSet("Referee - MED - Tchoukball, Squash")),
            new Person(new Name("Daniel Meier"), new Phone("87652533"), new Email("cornelia@example.com"),
                getRoleSet("Sponsor - OATSIDE")),
            new Person(new Name("Elle Meyer"), new Phone("9482224"), new Email("werner@example.com"),
                getRoleSet("Committee - Marketing - Project Director")),
            new Person(new Name("Fiona Kunz"), new Phone("9482427"), new Email("lydia@example.com"),
                getRoleSet("Committee - Publicity - Project Director")),
            new Person(new Name("George Best"), new Phone("9482442"), new Email("anna@example.com"),
                getRoleSet("Committee - Sports - Vice Sports Director - BIZ"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(Sport.CHESS,
                    new Pair<>(Faculty.COM, Faculty.NUSC),
                    new Venue("UT AUD2"),
                    ParserUtil.parseDateTime("2024 09 20 1000"),
                    Set.of(getSamplePersons()[0], getSamplePersons()[4])),
            new Event(Sport.SQUASH,
                    new Pair<>(Faculty.FASS, Faculty.YNC),
                    new Venue("Arena"),
                    ParserUtil.parseDateTime("2024 09 20 1400"),
                    Set.of(getSamplePersons()[2])),
            new Event(Sport.SWIMMING_M,
                    new Pair<>(Faculty.MED, Faculty.DEN),
                    new Venue("Pool"),
                    ParserUtil.parseDateTime("2024 09 20 1600"),
                    Set.of(getSamplePersons()[1], getSamplePersons()[5])),
            new Event(Sport.TABLE_TENNIS,
                    new Pair<>(Faculty.LAW, Faculty.BIZ),
                    new Venue("Court"),
                    ParserUtil.parseDateTime("2024 09 21 1000"),
                    Set.of(getSamplePersons()[3], getSamplePersons()[6]))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        return Arrays.stream(strings)
                .map(ParserUtil::parseRole)
                .collect(Collectors.toSet());
    }
}

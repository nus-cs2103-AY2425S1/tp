package seedu.eventfulnus.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.util.Pair;
import seedu.eventfulnus.logic.parser.ParserUtil;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.ReadOnlyAddressBook;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Email;
import seedu.eventfulnus.model.person.Name;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.Phone;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.Role;
import seedu.eventfulnus.model.person.role.athlete.Sport;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getRoleSet("Athlete - COM - Volleyball Women, Tennis")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getRoleSet("Volunteer - Photographer", "Committee - Publicity - Project Director")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getRoleSet("Sponsor - OATSIDE")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getRoleSet("Athlete - FASS - Swimming Men")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getRoleSet("Audience")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getRoleSet("Committee - Sports - Vice Sports Director - BIZ")),
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(Sport.CHESS,
                    new Pair<>(Faculty.COM, Faculty.NUSC),
                    new Venue("UT AUD2"),
                    ParserUtil.parseDateTime("2024 09 20 1000"),
                    Set.of(getSamplePersons()[0], getSamplePersons()[1])),
            new Event(Sport.SQUASH,
                    new Pair<>(Faculty.FASS, Faculty.YNC),
                    new Venue("Arena"),
                    ParserUtil.parseDateTime("2024 09 20 1400"),
                    Set.of(getSamplePersons()[2])),
            new Event(Sport.SWIMMING_M,
                    new Pair<>(Faculty.MED, Faculty.DEN),
                    new Venue("Pool"),
                    ParserUtil.parseDateTime("2024 09 20 1600"),
                    Set.of(getSamplePersons()[3], getSamplePersons()[4])),
            new Event(Sport.TABLE_TENNIS,
                    new Pair<>(Faculty.LAW, Faculty.BIZ),
                    new Venue("Court"),
                    ParserUtil.parseDateTime("2024 09 21 1000"),
                    Set.of(getSamplePersons()[5]))
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

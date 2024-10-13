package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Date;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Time;
import seedu.address.model.volunteer.Volunteer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Volunteer[] getSampleVolunteers() {
        return new Volunteer[] {
                new Volunteer(new Name("Alex Yeoh"), new Phone("12345678"), new Email("AlexYeoh@gmail.com"),
                        new Date("2022-02-02"), new Time("00:00"), new Time("00:01")),
                new Volunteer(new Name("Dominic"), new Phone("23456789"), new Email("dom@gmail.com"),
                        new Date("2022-03-02"), new Time("23:00"), new Time("00:32")),
                new Volunteer(new Name("Carry"), new Phone("12345633"), new Email("carry@gmail.com"),
                        new Date("2022-02-23"), new Time("00:00"), new Time("12:34")),
                new Volunteer(new Name("Dingsel"), new Phone("33345678"), new Email("dingsel@gmail.com"),
                        new Date("2022-04-02"), new Time("00:55"), new Time("12:01")),
                new Volunteer(new Name("Bagel"), new Phone("12232678"), new Email("bagel@gmail.com"),
                        new Date("2022-02-02"), new Time("00:00"), new Time("00:01")),
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
                new Event(new EventName("birthday"), new Location("12 Crescent Road"),
                        new seedu.address.model.event.Date("2002-02-02"), new seedu.address.model.event.Time("00:01"),
                        new seedu.address.model.event.Time("12:23")),
                new Event(new EventName("graduation"), new Location("23 Springfield Avenue"),
                        new seedu.address.model.event.Date("2004-03-02"), new seedu.address.model.event.Time("12:01"),
                        new seedu.address.model.event.Time("12:23")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Volunteer sampleVolunteer : getSampleVolunteers()) {
            sampleAb.addVolunteer(sampleVolunteer);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

}

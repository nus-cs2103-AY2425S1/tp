package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.volunteer.Date;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Volunteer[] getSampleVolunteers() {
        return new Volunteer[] {
            new Volunteer(new Name("Alex Yeoh"), new Phone("12345678"), new Email("AlexYeoh@gmail.com"),
                    new Date("2022-02-02")),
            new Volunteer(new Name("Bob Tan"), new Phone("23456789"), new Email("bob@gmail.com"),
                    new Date("2022-03-02")),
            new Volunteer(new Name("Cathy Lee"), new Phone("12345633"), new Email("cathy@gmail.com"),
                    new Date("2022-02-23")),
            new Volunteer(new Name("David Ng"), new Phone("33345678"), new Email("david@gmail.com"),
                    new Date("2022-04-02")),
            new Volunteer(new Name("Emily Lim"), new Phone("12232678"), new Email("emily@gmail.com"),
                    new Date("2022-02-02"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new EventName("Charity Run"), new Location("12 Crescent Road"),
                    new seedu.address.model.event.Date("2002-02-02"), new seedu.address.model.event.Time("00:01"),
                    new seedu.address.model.event.Time("12:23")),
            new Event(new EventName("Food Donation Drive"), new Location("Bishan Park"),
                    new seedu.address.model.event.Date("2004-03-02"), new seedu.address.model.event.Time("12:01"),
                    new seedu.address.model.event.Time("12:23")),
            new Event(new EventName("Blood Donation Drive"), new Location("Red Cross Centre"),
                    new seedu.address.model.event.Date("2014-04-02"), new seedu.address.model.event.Time("15:00"),
                    new seedu.address.model.event.Time("17:23")),
            new Event(new EventName("Graduation"), new Location("LT 28"),
                    new seedu.address.model.event.Date("2024-03-02"), new seedu.address.model.event.Time("07:00"),
                    new seedu.address.model.event.Time("14:23")),
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

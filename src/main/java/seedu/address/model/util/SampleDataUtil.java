package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.log.Log;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.IdentityNumber;
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
            new Person(new Name("Alex Yeoh"), new IdentityNumber("S7633494C"), new Phone("87438807"),
                new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), getLogSet("14 Oct 2023|First appointment")),
            new Person(new Name("Bernice Yu"), new IdentityNumber("S7140834E"), new Phone("99272758"),
                new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getLogSet("20 Jan 2024|Checkup due")),
            new Person(new Name("Charlotte Oliveiro"), new IdentityNumber("T0781984B"),
                new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getLogSet("10 Feb 2024|Second Appointment")),
            new Person(new Name("David Li"), new IdentityNumber("S5141265F"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getLogSet("15 Mar 2024|Checkin with patient")),
            new Person(new Name("Irfan Ibrahim"), new IdentityNumber("S6532483J"),
                new Phone("92492021"), new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getLogSet("22 Apr 2024|Second follow-up")),
            new Person(new Name("Roy Balakrishnan"), new IdentityNumber("T1355313G"),
                new Phone("92624417"), new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getLogSet("22 Apr 2024|Meditation Techniques"))
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a log set containing the list of log details given.
     * Each string should be in the format "date|details".
     */
    public static Set<Log> getLogSet(String... logs) {
        return Arrays.stream(logs)
                .map(log -> {
                    String[] logParts = log.split("\\|", 2);
                    if (logParts.length != 2) {
                        throw new IllegalArgumentException("Log format is invalid: " + log);
                    }
                    String dateStr = logParts[0].trim();
                    String details = logParts[1].trim();
                    if (dateStr.isEmpty()) {
                        throw new IllegalArgumentException("Log format has missing date."
                                + Log.MESSAGE_CONSTRAINTS);
                    }
                    AppointmentDate appointmentDate = new AppointmentDate(dateStr);
                    if (details.isEmpty()) {
                        throw new IllegalArgumentException("Log format has missing entry."
                                + Log.MESSAGE_CONSTRAINTS);
                    }

                    return new Log(appointmentDate, details);
                })
                .collect(Collectors.toSet());
    }
}

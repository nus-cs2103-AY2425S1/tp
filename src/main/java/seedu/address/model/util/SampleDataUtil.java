package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Role("brUdder"), new Major("cs"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Role("mUdder"), new Major("bza"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Role("brUdder"), new Major("isys"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Role("mUdder"), new Major("cs"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Role("brUdder"), new Major("bza"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Role("brUdder"), new Major("cs"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues")),
            new Person(new Name("John Mayer"), new Phone("92658789"), new Email("jmayer@example.com"),
                new Role("mUdder"), new Major("cs"),
                new Address("Blk 63 Kent Ridge Avenue 3, #3-22"),
                getTagSet("classmates")),
            new Person(new Name("John Nissins"), new Phone("89800869"), new Email("theoneandjohnny@example.com"),
                new Role("brUdder"), new Major("isys"),
                new Address("Blk 420 Aljunied Street 69, #9-11"),
                getTagSet("classmates"))
        };
    }

    public static Meeting[] getSampleMeetings() {
        Meeting[] newMeetings;

        try {
            newMeetings = new Meeting[]{
                new Meeting(new Name("Alex Yeoh"),
                    LocalDateTime.of(LocalDate.of(2024, 10, 9), LocalTime.of(9, 0, 0)),
                    LocalDateTime.of(LocalDate.of(2024, 10, 9), LocalTime.of(10, 0, 0)),
                    "The Terrace"),
                new Meeting(new Name("David Li"),
                    LocalDateTime.of(LocalDate.of(2024, 10, 10), LocalTime.of(14, 0, 0)),
                    LocalDateTime.of(LocalDate.of(2024, 10, 10), LocalTime.of(16, 0, 0)),
                    "COM-01"),
                new Meeting(new Name("Alex Yeoh"),
                    LocalDateTime.of(LocalDate.of(2024, 10, 11), LocalTime.of(12, 0, 0)),
                    LocalDateTime.of(LocalDate.of(2024, 10, 11), LocalTime.of(13, 30, 0)),
                    "The Terrace"),
                new Meeting(new Name("John Mayer"),
                    LocalDateTime.of(LocalDate.of(2024, 10, 11), LocalTime.of(14, 0, 0)),
                    LocalDateTime.of(LocalDate.of(2024, 10, 11), LocalTime.of(15, 0, 0)),
                    "Our Tutorial Class")
            };
        } catch (CommandException var1) {
            newMeetings = new Meeting[]{};
        }
        return newMeetings;
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

        for (Meeting sampleMeeting : getSampleMeetings()) {
            Name name = sampleMeeting.getPersonToMeet();
            for (Person person : sampleAb.getPersonList()) {
                if (person.getName().equals(name)) {
                    person.getMeetings().addMeeting(sampleMeeting);
                }
            }
            sampleAb.addMeeting(sampleMeeting);
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

}

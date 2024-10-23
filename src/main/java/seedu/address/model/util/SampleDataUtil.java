package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyScheduleList;
import seedu.address.model.ScheduleList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.schedule.Meeting;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
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

    public static Meeting[] getSampleMeetings(ReadOnlyAddressBook addressBook) {
        List<Person> persons = addressBook.getPersonList();
        assert(persons.size() > 0);
        Random random = new Random();
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        return new Meeting[] {
            new Meeting(
            List.of(persons.get(random.nextInt(persons.size())).getUid()),
            "Meeting 1",
            nowDate,
            nowTime),
            new Meeting(
            List.of(persons.get(random.nextInt(persons.size())).getUid()),
            "Meeting 2",
            nowDate.plusDays(1),
            nowTime.plusHours(1)),
            new Meeting(
            List.of(persons.get(random.nextInt(persons.size())).getUid()),
            "Meeting 3",
            nowDate.plusDays(2),
            nowTime.plusHours(2)),
            new Meeting(
            List.of(persons.get(random.nextInt(persons.size())).getUid()),
            "Meeting 4",
            nowDate.plusDays(3),
            nowTime.plusHours(3)),
            new Meeting(
            List.of(persons.get(random.nextInt(persons.size())).getUid()),
            "Meeting 5",
            nowDate.plusDays(4),
            nowTime.plusHours(4)),
            new Meeting(
            List.of(persons.get(random.nextInt(persons.size())).getUid()),
            "Meeting 6",
            nowDate.plusDays(5),
            nowTime.plusHours(5)),
            new Meeting(
            List.of(persons.get(random.nextInt(persons.size())).getUid()),
            "Meeting 7",
            nowDate.plusDays(6),
            nowTime.plusHours(6))
        };
    }

    public static ReadOnlyScheduleList getSampleScheduleList(ReadOnlyAddressBook addressBook) {
        ScheduleList sampleSl = new ScheduleList();
        for (Meeting sampleMeeting : getSampleMeetings(addressBook)) {
            sampleSl.addMeeting(sampleMeeting);
        }
        return sampleSl;
    }
}

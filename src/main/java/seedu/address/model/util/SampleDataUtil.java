package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyReminderAddressBook;
import seedu.address.model.ReminderAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.LastSeen;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organisation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Reminder;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} and {@code ReminderAddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Organisation("NUS"), new LastSeen("22-09-2024"),
                getTagSet("friends"), new Priority("low"), new Remark("likes apple")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Organisation("NTU"), new LastSeen("19-01-2024"), getTagSet("colleagues", "friends"),
                       new Priority("medium"), new Remark("likes apple")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Organisation("Tik Tok"), new LastSeen("13-07-2024"), getTagSet("neighbours"),
                    new Priority("high"), new Remark("pretty")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Organisation("Meta"), new LastSeen("01-01-2022"), getTagSet("family"),
                    new Priority("low"), new Remark("handsome")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Organisation("NUS"), new LastSeen("28-04-2009"), getTagSet("classmates"),
                    new Priority("medium"), new Remark("handsome")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Organisation("NUS"), new LastSeen("11-03-2023"), getTagSet("colleagues"),
                    new Priority("high"), new Remark("handsome"))
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

    public static Reminder[] getSampleReminders() {
        return new Reminder[] {
            new Reminder("10-01-2025", "breakfast", getSamplePersons()[0].getName()),
            new Reminder("11-02-2025", "lunch", getSamplePersons()[1].getName()),
            new Reminder("12-01-2025", "dinner", getSamplePersons()[2].getName()),
            new Reminder("13-01-2025", "meeting", getSamplePersons()[3].getName()),
            new Reminder("14-03-2025", "gym", getSamplePersons()[4].getName()),
            new Reminder("15-04-2025", "meeting", getSamplePersons()[5].getName())
        };
    }

    public static ReadOnlyReminderAddressBook getSampleReminderAddressBook() {
        ReminderAddressBook sampleRab = new ReminderAddressBook();
        for (Reminder sampleReminder : getSampleReminders()) {
            sampleRab.addReminder(sampleReminder);
        }
        return sampleRab;
    }
}

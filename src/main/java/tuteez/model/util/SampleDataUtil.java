package tuteez.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import tuteez.model.AddressBook;
import tuteez.model.ReadOnlyAddressBook;
import tuteez.model.person.Address;
import tuteez.model.person.Email;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.model.person.Phone;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), TelegramUsername.of("alex_yeoh"),
                getTagSet("Chemistry"), getLessonLst("monday 0900-1000")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), TelegramUsername.of("bernice_yu"),
                getTagSet("Physics"), getLessonLst("tuesday 1000-1100")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), TelegramUsername.of("charlotte_oliveiro"),
                getTagSet("Math"), getLessonLst("wednesday 1000-1200")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), TelegramUsername.of("david_li"),
                getTagSet("English"), getLessonLst("friday 1000-1100")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), TelegramUsername.of("irfan_ibrahim"),
                getTagSet("Biology"), getLessonLst("saturday 0900-1100")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), TelegramUsername.of("roy_balakrishnan"),
                getTagSet("Science"), getLessonLst("sunday 0900-1100"))
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
     * Returns a Lesson set containing the list of strings given.
     */
    public static List<Lesson> getLessonLst(String... strings) {
        return Arrays.stream(strings)
                .map(Lesson::new)
                .collect(Collectors.toList());
    }
}

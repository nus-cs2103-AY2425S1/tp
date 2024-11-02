package tutorease.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import tutorease.address.model.LessonSchedule;
import tutorease.address.model.ReadOnlyLessonSchedule;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.TutorEase;
import tutorease.address.model.person.Address;
import tutorease.address.model.person.Email;
import tutorease.address.model.person.Name;
import tutorease.address.model.person.Person;
import tutorease.address.model.person.Phone;
import tutorease.address.model.person.Role;
import tutorease.address.model.person.Student;
import tutorease.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code TutorEase} with sample data.
 */
public class SampleDataUtil {
    public static final Role STUDENT_ROLE = new Role("Student");
    public static final Role GAURDIAN_ROLE = new Role("Guardian");
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), STUDENT_ROLE,
                getTagSet("friends")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), STUDENT_ROLE,
                getTagSet("colleagues", "friends")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), STUDENT_ROLE,
                getTagSet("neighbours")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), GAURDIAN_ROLE,
                getTagSet("family")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), STUDENT_ROLE,
                getTagSet("classmates")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), STUDENT_ROLE,
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyTutorEase getSampleTutorEase() {
        TutorEase sampleAb = new TutorEase();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyLessonSchedule getSampleLessonSchedule() {
        return new LessonSchedule();
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


package seedu.address.model.util;

import seedu.address.model.EduContacts;
import seedu.address.model.ReadOnlyEduContacts;
import seedu.address.model.person.Address;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Tag;

/**
 * Contains utility methods for populating {@code EduContacts} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new StudentId("12121212"), new Name("Alex Yeoh"),
                    new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Course("Geography"),
                    new Tag("Student")),
            new Person(new StudentId("13131313"),
                    new Name("Bernice Yu"),
                    new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Course("Computer Science"),
                    new Tag("Student")),
            new Person(new StudentId("23461293"),
                    new Name("Charlotte Oliveiro"),
                    new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Course("Data Science"),
                    new Tag("Student")),
            new Person(new StudentId("98881999"),
                    new Name("David Li"),
                    new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Course("Law"),
                    new Tag("Student")),
            new Person(new StudentId("87778888"),
                    new Name("Irfan Ibrahim"),
                    new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Course("Medicine"),
                    new Tag("Student")),
            new Person(new StudentId("98189818"),
                    new Name("Roy Balakrishnan"),
                    new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Course("Nursing"),
                    new Tag("Student"))
        };
    }

    public static ReadOnlyEduContacts getSampleEduContacts() {
        EduContacts sampleAb = new EduContacts();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }
}

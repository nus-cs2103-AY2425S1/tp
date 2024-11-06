package seedu.academyassist.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.person.Address;
import seedu.academyassist.model.person.Email;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Name;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Phone;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.SubjectEnum;
import seedu.academyassist.model.person.YearGroup;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Ic("F1234568X"), new YearGroup("1"),
                StudentId.generateNewStudentId(1), getSubjectSet("English")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Ic("F1234569X"), new YearGroup("2"),
                StudentId.generateNewStudentId(2), getSubjectSet("English")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Ic("F1236567X"), new YearGroup("1"),
                StudentId.generateNewStudentId(3), getSubjectSet("English")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Ic("F1134567X"), new YearGroup("3"),
                StudentId.generateNewStudentId(4), getSubjectSet("English")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Ic("F1234777X"), new YearGroup("1"),
                StudentId.generateNewStudentId(5), getSubjectSet("English")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Ic("F1235567X"), new YearGroup("5"),
                StudentId.generateNewStudentId(6), getSubjectSet("English"))
        };
    }

    public static ReadOnlyAcademyAssist getSampleAcademyAssist() {
        AcademyAssist sampleAa = new AcademyAssist();
        for (Person samplePerson : getSamplePersons()) {
            sampleAa.addPerson(samplePerson);
            sampleAa.setIdGeneratedCount(6);
        }
        return sampleAa;
    }

    /**
     * Returns a subject set containing the list of strings given.
     */
    public static Set<Subject> getSubjectSet(String... strings) {
        return Arrays.stream(strings)
                .filter(SubjectEnum::isValidSubject)
                .map(Subject::new)
                .collect(Collectors.toSet());
    }

}

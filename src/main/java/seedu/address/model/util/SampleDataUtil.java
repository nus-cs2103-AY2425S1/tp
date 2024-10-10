package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSamplePersons() {
        return new Student[]{
            new Student(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"),
                getTagSet("friends"), new StudentNumber("A0737935G")),
            new Student(new Name("Bernice Yu"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends"), new StudentNumber("A0597991H")),
            new Student(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"),
                getTagSet("neighbours"), new StudentNumber("A0632228J")),
            new Student(new Name("David Li"), new Email("lidavid@example.com"),
                getTagSet("family"), new StudentNumber("A0965321G")),
            new Student(new Name("Irfan Ibrahim"), new Email("irfan@example.com"),
                getTagSet("classmates"), new StudentNumber("A0467953H")),
            new Student(new Name("Roy Balakrishnan"), new Email("royb@example.com"),
                getTagSet("colleagues"), new StudentNumber("A0122764H"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSamplePersons()) {
            sampleAb.addStudent(sampleStudent);
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

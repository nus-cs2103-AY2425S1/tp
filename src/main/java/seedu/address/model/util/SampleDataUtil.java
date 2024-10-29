package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Address;
import seedu.address.model.student.EmergencyContact;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;
import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskDeadline;
import seedu.address.model.student.task.TaskDescription;
import seedu.address.model.student.task.TaskList;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static TaskList tl = new TaskList();
    public static Student[] getSampleStudents() {
        tl.add(new Task(new TaskDescription("Mark homework"),
                new TaskDeadline("2024-12-31")));

        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new EmergencyContact("87438807"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Note(""),
                getSubjectSet("MATH"), new Level("S1 NA"), tl,
                getLessonTimeSet("MON-17:00-19:00")),

            new Student(new Name("Bernice Yu"), new Phone("99272758"), new EmergencyContact("99272758"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Note(""),
                getSubjectSet("PHYSICS", "MATH"), new Level("S2 NT"), new TaskList(),
                getLessonTimeSet("TUE-17:00-19:00", "SUN-17:00-19:00")),

            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new EmergencyContact("93210283"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Note(""),
                getSubjectSet("CHEMISTRY"), new Level("S3 NA"), new TaskList(),
                getLessonTimeSet("WED-17:00-19:00")),

            new Student(new Name("David Li"), new Phone("91031282"), new EmergencyContact("93210283"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Note(""),
                getSubjectSet("BIOLOGY"), new Level("S4 EXPRESS"), new TaskList(),
                getLessonTimeSet("MON-11:00-13:00")),

            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new EmergencyContact("92624417"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Note(""),
                getSubjectSet("MATH"), new Level("S4 NA"), new TaskList(),
                getLessonTimeSet("THU-17:00-19:00")),

            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new EmergencyContact("92624417"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Note("very disobedient"),
                getSubjectSet("PHYSICS"), new Level("S4 NA"), new TaskList(),
                getLessonTimeSet("SAT-17:00-19:00"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    /**
     * Returns a subject set containing the list of strings given.
     */
    public static Set<Subject> getSubjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Subject::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a lesson time set containing the list of strings given.
     */
    public static Set<LessonTime> getLessonTimeSet(String... strings) {
        return Arrays.stream(strings)
                .map(LessonTime::new)
                .collect(Collectors.toSet());
    }

}

package seedu.edulog.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.edulog.model.EduLog;
import seedu.edulog.model.ReadOnlyEduLog;
import seedu.edulog.model.calendar.Day;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;
import seedu.edulog.model.student.Address;
import seedu.edulog.model.student.Email;
import seedu.edulog.model.student.Fee;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Phone;
import seedu.edulog.model.student.Student;
import seedu.edulog.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EduLog} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), new Fee("100")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), new Fee("200")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new Fee("100")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new Fee("100")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), new Fee("100")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new Fee("100"))
        };
    }

    public static ReadOnlyEduLog getSampleEduLog() {
        EduLog sampleEdulog = new EduLog();
        for (Student sampleStudent : getSampleStudents()) {
            sampleEdulog.addStudent(sampleStudent);
        }

        for (Lesson l: getSampleLessons()) {
            sampleEdulog.addLesson(l);
        }

        return sampleEdulog;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    private static Lesson[] getSampleLessons() {
        return new Lesson[] {
            new Lesson(new Description("Sec 4 Chemistry Class A"), new Day("Monday"),
                new LessonTime("2000"), new LessonTime("2200")),
            new Lesson(new Description("Late night math catchup for P6 students"), new Day("Friday"),
                new LessonTime("2315"), new LessonTime("0045")),
            new Lesson(new Description("(Relief slot) Sec 4 Chemistry Class B"), new Day("Saturday"),
                new LessonTime("2030"), new LessonTime("2300"))
        };
    }

}

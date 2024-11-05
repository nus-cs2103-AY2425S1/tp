package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.course.Course;
import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.StudentLessonInfo;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Student ALEX = new Student(new Name("Alex Yeoh"),
            new Phone("87438807"), new Email("alexyeoh@example.com"),
            getCourseSet("cs2103t"));
    public static final Student BERNICE = new Student(new Name("Bernice Yu"),
            new Phone("99272758"), new Email("berniceyu@example.com"),
            getCourseSet("cs2103t", "cs2109s"));
    public static final Student CHARLOTTE = new Student(new Name("Charlotte Oliveiro"),
            new Phone("93210283"), new Email("charlotte@example.com"),
            getCourseSet("ma1100"));
    public static final Student DAVID = new Student(new Name("David Li"),
            new Phone("91031282"), new Email("lidavid@example.com"),
            getCourseSet("ma1100", "ma2104", "ma2108"));
    public static final Student IRFAN = new Student(new Name("Irfan Ibrahim"),
            new Phone("92492021"), new Email("irfan@example.com"),
            getCourseSet("CS3230", "CS3263", "CS3264"));
    public static final Student ROY = new Student(new Name("Roy Balakrishnan"),
            new Phone("92624417"), new Email("royb@example.com"),
            getCourseSet("CS4248", "CS4277"));

    public static Student[] getSampleStudents() {
        return new Student[] {ALEX, BERNICE, CHARLOTTE, DAVID, IRFAN, ROY};
    }

    public static Consultation[] getSampleConsultations() {
        return new Consultation[] {
            new Consultation(new Date("2024-11-02"), new Time("10:00"), List.of(ALEX)),
            new Consultation(new Date("2024-11-09"), new Time("11:00"), List.of(BERNICE, ROY)),
            new Consultation(new Date("2024-11-16"), new Time("12:00"), List.of(ALEX, BERNICE, CHARLOTTE)),
            new Consultation(new Date("2024-11-23"), new Time("13:00"), List.of(DAVID)),
            new Consultation(new Date("2024-11-30"), new Time("14:00"), List.of(IRFAN, ROY))
        };
    }

    public static Lesson[] getSampleLessons() {
        return new Lesson[] {
            new Lesson(new Date("2024-11-01"), new Time("13:00"), List.of(
                new StudentLessonInfo(ALEX, true, 3),
                new StudentLessonInfo(BERNICE, true, 3),
                new StudentLessonInfo(ROY, false, 0)
            )),
            new Lesson(new Date("2024-11-08"), new Time("13:00"), List.of(
                new StudentLessonInfo(DAVID, true, 6),
                new StudentLessonInfo(IRFAN, true, 4)
            )),
            new Lesson(new Date("2024-11-15"), new Time("13:00"), List.of(
                new StudentLessonInfo(ALEX, false, 0),
                new StudentLessonInfo(IRFAN, false, 0),
                new StudentLessonInfo(CHARLOTTE, false, 0)
            )),
            new Lesson(new Date("2024-11-22"), new Time("13:00"), List.of(
                new StudentLessonInfo(IRFAN, false, 0)
            )),
            new Lesson(new Date("2024-11-29"), new Time("13:00"), List.of())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        for (Consultation sampleConsult : getSampleConsultations()) {
            sampleAb.addConsult(sampleConsult);
        }
        for (Lesson sampleLesson : getSampleLessons()) {
            sampleAb.addLesson(sampleLesson);
        }
        return sampleAb;
    }

    /**
     * Returns a course set containing the list of strings given.
     */
    public static Set<Course> getCourseSet(String... strings) {
        return Arrays.stream(strings)
                .map(Course::new)
                .collect(Collectors.toSet());
    }

}

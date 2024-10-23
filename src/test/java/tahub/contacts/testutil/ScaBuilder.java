package tahub.contacts.testutil;

import static tahub.contacts.testutil.AttendanceExamples.ATTENDANCE_EXAMPLE_1;

import java.util.HashSet;

import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.grade.GradingSystem;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.tutorial.Tutorial;

/**
 * A utility class to help build sample {@link tahub.contacts.model.studentcourseassociation.StudentCourseAssociation}
 * objects for testing.
 */
public class ScaBuilder {
    /**
     * Creates a {@link StudentCourseAssociation} with the standard details.
     */
    public static StudentCourseAssociation createDefault() {
        Person student = new Person(
                new MatriculationNumber("A1234567A"),
                new Name("John Tan"),
                new Phone("12345678"),
                new Email("johnt@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("CS1000"), new CourseName("Computer Testing"));
        Tutorial tutorial = new Tutorial("T1", course);
        return new StudentCourseAssociation(student, course, tutorial, new GradingSystem(), ATTENDANCE_EXAMPLE_1);
    }

    /**
     * Creates another sample {@link StudentCourseAssociation} with different details.
     */
    public static StudentCourseAssociation createSampleSecond() {
        Person student = new Person(
                new MatriculationNumber("A2434567A"),
                new Name("John Tans"),
                new Phone("12345679"),
                new Email("johnt@notexample.com"),
                new Address("Computing 2, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("CS1001"), new CourseName("Computer Testing"));
        Tutorial tutorial = new Tutorial("T1", course);
        return new StudentCourseAssociation(student, course, tutorial, new GradingSystem(), ATTENDANCE_EXAMPLE_1);
    }
}

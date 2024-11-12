package tahub.contacts.model.studentcourseassociation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.testutil.AttendanceExamples.ATTENDANCE_EXAMPLE_1;

import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;
import tahub.contacts.model.tutorial.Tutorial;
import tahub.contacts.testutil.PersonBuilder;


class StudentCourseAssociationTest {

    //=========== Constructor without GradingSystem and Attendance ====================================================
    private static final Person TEST_PERSON_1 = new Person(
            new MatriculationNumber("A1234567A"),
            new Name("Prof Zee"),
            new Phone("12345678"),
            new Email("zee@hotmail.com"),
            new Address("Computing 1, 13 Computing Dr, 117417"),
            new HashSet<>()
    );

    private static final Course TEST_COURSE_1 = new Course(
            new CourseCode("GA2030"),
            new CourseName("Computer Gaming II")
    );

    private static final Course TEST_COURSE_2 = new Course(
            new CourseCode("GA3230"),
            new CourseName("Design and Analysis of Games")
    );

    private static final Tutorial TEST_TUTORIAL_1 = new Tutorial(
            "T16",
            TEST_COURSE_1
    );

    private static final Tutorial TEST_TUTORIAL_2 = new Tutorial(
            "T17",
            TEST_COURSE_1
    );

    @Test
    public void isSameSca() {
        StudentCourseAssociation sca1 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_1, TEST_TUTORIAL_1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_1, TEST_TUTORIAL_1);
        assertEquals(sca1, sca2);

        StudentCourseAssociation sca3 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_1, TEST_TUTORIAL_1,
                                                                     ATTENDANCE_EXAMPLE_1); // Same primary keys
        assertEquals(sca1, sca3);
    }

    @Test
    public void isSameScaDifferentConstructor() {
        StudentCourseAssociation sca1 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_1, TEST_TUTORIAL_1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_1, TEST_TUTORIAL_1,
                                                                     ATTENDANCE_EXAMPLE_1); // Same primary keys
        assertEquals(sca1, sca2);
    }

    @Test
    public void isDifferentSca_tutorialDifferent() {
        StudentCourseAssociation sca1 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_1, TEST_TUTORIAL_1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_1, TEST_TUTORIAL_2);
        assertNotEquals(sca1, sca2);
    }

    public void isDifferentSca_courseDifferent() {
        StudentCourseAssociation sca1 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_1, TEST_TUTORIAL_1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(TEST_PERSON_1, TEST_COURSE_2, TEST_TUTORIAL_1);
        assertNotEquals(sca1, sca2);
    }

    //=========== Constructor with GradingSystem and Attendance =======================================================
    @Test
    public void testConstructorWithAllFields() {
        Person student = new Person(
                new MatriculationNumber("A1234567A"),
                new Name("Prof Alex Siow"),
                new Phone("12345678"),
                new Email("alexs@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("CS2100"), new CourseName("Computer Organisation"));
        Tutorial tutorial = new Tutorial("T1", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(tutorial, sca.getTutorial());
    }
    @Test
    public void testEqualsMethod() {
        Person student = new Person(
                new MatriculationNumber("A1234567C"),
                new Name("Prof Chan Wing Cheong"),
                new Phone("12345678"),
                new Email("chanwc@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("CS3230"), new CourseName("Design and Analysis of Algorithms"));
        Tutorial tutorial = new Tutorial("T2", course);

        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        assertEquals(sca1, sca2);

        Tutorial differentTutorial = new Tutorial("T3", course);
        StudentCourseAssociation sca3 = new StudentCourseAssociation(
                student, course, differentTutorial, ATTENDANCE_EXAMPLE_1);
        assertFalse(sca1.equals(sca3));
    }

    @Test
    public void testConstructorWithDifferentCourseAndTutorial() {
        Person student = new Person(
                new MatriculationNumber("A1234567E"),
                new Name("Prof Ewe Chun Peng"),
                new Phone("12345678"),
                new Email("ecp@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("IS1103"), new CourseName("Computing and Society"));
        Tutorial tutorial = new Tutorial("T5", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        Course courseDiff = new Course(new CourseCode("IS1108"), new CourseName("Unethical Computing"));
        Tutorial tutorialDiff = new Tutorial("T77", course);

        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(tutorial, sca.getTutorial());
        assertNotEquals(courseDiff, sca.getCourse());
        assertNotEquals(tutorialDiff, sca.getTutorial());
    }
    @Test
    public void testEqualsMethodWithDifferentCourses() {
        Person student = new Person(
                new MatriculationNumber("A1234567G"),
                new Name("Prof Goh Kan Eng"),
                new Phone("12345678"),
                new Email("gohke@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course1 = new Course(new CourseCode("IS1112"), new CourseName("E Commerce"));
        Course course2 = new Course(new CourseCode("IS1122"), new CourseName("Digital Transformation"));
        Tutorial tutorial = new Tutorial("T6", course1);

        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course1, tutorial, ATTENDANCE_EXAMPLE_1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(student, course2, tutorial, ATTENDANCE_EXAMPLE_1);
        assertFalse(sca1.equals(sca2));
    }

    @Nested
    @DisplayName("isSameSca")
    class IsSameSca {
        private Person stuRef = new PersonBuilder().build();
        private Person stuDiffMatricNo = new PersonBuilder().withMatriculationNumber("A9999999X").build();
        private Person stuDiffName = new PersonBuilder().withName("Name Different").build();
        private Course courseRef = new Course(new CourseCode("AA1000"), new CourseName("Reference Course"));
        private Course courseDiffName = new Course(new CourseCode("AA1000"), new CourseName("Different Name"));
        private Course courseDiffCode = new Course(new CourseCode("XX9999"), new CourseName("Reference Course"));
        private Tutorial tutorialRef = new Tutorial("T01", courseRef);
        private Tutorial tutorialDiffId = new Tutorial("T22", courseRef);
        private Tutorial tutorialDiffCourse = new Tutorial("T01", courseDiffCode);

        @Test
        @DisplayName("returns true when SCAs have same student, course, tutorial object")
        public void returnsTrue_sameStudentCourseTutorial() {
            StudentCourseAssociation sca1 = new StudentCourseAssociation(stuRef, courseRef, tutorialRef);
            StudentCourseAssociation sca2 = new StudentCourseAssociation(stuRef, courseRef, tutorialRef);
            assertTrue(sca1.isSameSca(sca2));
        }

        @Test
        @DisplayName("returns true when SCAs have the same IDs for student, course, tutorial but different details")
        public void returnsTrue_sameIdsButDiffDetails() {
            StudentCourseAssociation sca1 = new StudentCourseAssociation(stuRef, courseRef, tutorialRef);
            StudentCourseAssociation sca2 = new StudentCourseAssociation(
                    stuDiffName, courseDiffName, tutorialDiffCourse);
            assertFalse(sca1.isSameSca(sca2));
        }

        @Test
        @DisplayName("returns false when SCAs have different IDs for student, course, tutorial")
        public void returnsTrue_differentIds() {
            StudentCourseAssociation sca1 = new StudentCourseAssociation(stuRef, courseRef, tutorialRef);
            StudentCourseAssociation sca2 = new StudentCourseAssociation(
                    stuDiffMatricNo, courseDiffCode, tutorialDiffId);
            assertFalse(sca1.isSameSca(sca2));
        }
    }

    @Test
    public void testGetCourseMethod() {
        Person student = new Person(
                new MatriculationNumber("A1234567H"),
                new Name("Prof Ho Kah Chun"),
                new Phone("12345678"),
                new Email("hkc@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("IS1131"), new CourseName("Financial Management"));
        Tutorial tutorial = new Tutorial("T14", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        Course retrievedCourse = sca.getCourse();
        assertSame(course, retrievedCourse);
    }
}

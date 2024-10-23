package tahub.contacts.model.studentcourseassociation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.testutil.AttendanceExamples.ATTENDANCE_EXAMPLE_1;

import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.Test;

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
import tahub.contacts.model.tutorial.Tutorial;



class StudentCourseAssociationTest {
    @Test
    public void testConstructorWithCourseAndTutorial() {
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
    public void testGetGradesMethod() {
        Person student = new Person(
                new MatriculationNumber("A1234567D"),
                new Name("Prof Damith C Rajapakse"),
                new Phone("12345678"),
                new Email("damithcr@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("CS2103T"), new CourseName("Software Engineering"));
        Tutorial tutorial = new Tutorial("T4", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        GradingSystem grades = sca.getGrades();
        assertNotNull(grades);
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

    @Test
    void testAddGrade() {
        Person student = new Person(
                new MatriculationNumber("A1234556J"),
                new Name("Prof John Doe"),
                new Phone("12345678"),
                new Email("johndoe@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("IS1131"), new CourseName("Financial Management"));
        Tutorial tutorial = new Tutorial("T7", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        sca.addGrade("Midterm", 85.0);
        assertEquals(85.0, sca.getGrade("Midterm"), 0.001);
    }

    @Test
    void testGetLetterGrade() {
        Person student = new Person(
                new MatriculationNumber("A1234556J"),
                new Name("Prof John Doe"),
                new Phone("12345678"),
                new Email("johndoe@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("IS2102"), new CourseName("Financial Management"));
        Tutorial tutorial = new Tutorial("T4", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);
    }

    @Test
    void testSetAssessmentWeight() {
        Person student = new Person(
                new MatriculationNumber("A1234556J"),
                new Name("Prof John Doe"),
                new Phone("12345678"),
                new Email("johndoe@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course(new CourseCode("IS1131"), new CourseName("Financial Management"));
        Tutorial tutorial = new Tutorial("T4", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        sca.addGrade("Midterm", 85.0);
        sca.addGrade("Final", 95.0);
        sca.setAssessmentWeight("Midterm", 0.4);
        sca.setAssessmentWeight("Final", 0.6);

        assertEquals(91.0, sca.getOverallScore(), 0.001);
    }

    @Test
    void testGetOverallScore() {
        Person student = createTestPerson("A1234567K", "Prof Kelly Tan");
        Course course = new Course(new CourseCode("IS1131"), new CourseName("Financial Management"));
        Tutorial tutorial = new Tutorial("T8", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        sca.addGrade("Midterm", 85.0);
        sca.addGrade("Final", 95.0);
        sca.setAssessmentWeight("Midterm", 0.4);
        sca.setAssessmentWeight("Final", 0.6);

        assertEquals(91.0, sca.getOverallScore(), 0.001);
    }

    @Test
    void testGetAllGrades() {
        Person student = createTestPerson("A1234567L", "Prof Lim Ah Seng");
        Course course = new Course(new CourseCode("IS1131"), new CourseName("Financial Management"));
        Tutorial tutorial = new Tutorial("T9", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial, ATTENDANCE_EXAMPLE_1);

        sca.addGrade("Midterm", 85.0);
        sca.addGrade("Final", 95.0);

        Map<String, Double> allGrades = sca.getAllGrades();
        assertEquals(2, allGrades.size());
        assertTrue(allGrades.containsKey("Midterm"));
        assertTrue(allGrades.containsKey("Final"));
        assertEquals(85.0, allGrades.get("Midterm"), 0.001);
        assertEquals(95.0, allGrades.get("Final"), 0.001);
    }

    private Person createTestPerson(String matriculationNumber, String name) {
        return new Person(
                new MatriculationNumber(matriculationNumber),
                new Name(name),
                new Phone("12345678"),
                new Email(name.toLowerCase().replace(" ", "") + "@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
    }
}

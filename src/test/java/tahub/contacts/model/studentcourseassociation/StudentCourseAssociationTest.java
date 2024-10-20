package tahub.contacts.model.studentcourseassociation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import tahub.contacts.model.course.Course;
import tahub.contacts.model.courseclass.recitation.Recitation;
import tahub.contacts.model.courseclass.tutorial.Tutorial;
import tahub.contacts.model.grade.GradingSystem;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;

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
        Course course = new Course("CS2100", "Computer Organisation");
        Tutorial tutorial = new Tutorial("T1", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial);

        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(tutorial, sca.getCourseClass());
    }
    @Test
    public void testConstructorWithCourseAndRecitation() {
        Person student = new Person(
                new MatriculationNumber("A1234567B"),
                new Name("Prof Ben Leong"),
                new Phone("12345678"),
                new Email("benl@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course("CS2102", "Database Systems");
        Recitation recitation = new Recitation("R1", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, recitation);

        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(recitation, sca.getCourseClass());
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
        Course course = new Course("CS3230", "Design and Analysis of Algorithms");
        Tutorial tutorial = new Tutorial("T2", course);

        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course, tutorial);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(student, course, tutorial);

        assertEquals(sca1, sca2);

        Tutorial differentTutorial = new Tutorial("T3", course);
        StudentCourseAssociation sca3 = new StudentCourseAssociation(student, course, differentTutorial);
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
        Course course = new Course("CS2103T", "Software Engineering");
        Tutorial tutorial = new Tutorial("T4", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial);

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
        Course course = new Course("IS1103", "Computing and Society");
        Tutorial tutorial = new Tutorial("T5", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial);

        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(tutorial, sca.getCourseClass());
    }
    @Test
    public void testConstructorWithDifferentCourseAndRecitation() {
        Person student = new Person(
                new MatriculationNumber("A1234567F"),
                new Name("Prof Foo Yee Shoon"),
                new Phone("12345678"),
                new Email("foos@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course("IS1105", "Strategic Financial Management");
        Recitation recitation = new Recitation("R2", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, recitation);

        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(recitation, sca.getCourseClass());
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
        Course course1 = new Course("IS1112", "E Commerce");
        Course course2 = new Course("IS1122", "Digital Transformation");
        Tutorial tutorial = new Tutorial("T6", course1);

        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course1, tutorial);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(student, course2, tutorial);
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
        Course course = new Course("IS1131", "Financial Management");
        Recitation recitation = new Recitation("R3", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, recitation);

        Course retrievedCourse = sca.getCourse();
        assertSame(course, retrievedCourse);
    }

    @Test
    void testAddGrade() {
        Person student = new Person(
                new MatriculationNumber("A1234556I"),
                new Name("Prof Ian Tsang"),
                new Phone("12345678"),
                new Email("iantsang@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course("IS2102", "Financial Management");
        Tutorial tutorial = new Tutorial("T7", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial);

        sca.addGrade("Midterm", 85.0);
        assertEquals(85.0, sca.getGrades().getGrade("Midterm"));
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
        Course course = new Course("IS2102", "Financial Management");
        Recitation recitation = new Recitation("R4", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, recitation);

        sca.addGrade("Midterm", 85.0);
        assertEquals("B", sca.getLetterGrade());
    }

    @Test
    void testGetOverallScore() {
        Person student = new Person(
                new MatriculationNumber("A1234556K"),
                new Name("Prof Kelly Tan"),
                new Phone("12345678"),
                new Email("kellytan@example.com"),
                new Address("Computing 1, 13 Computing Dr, 117417"),
                new HashSet<>()
        );
        Course course = new Course("IS2102", "Financial Management");
        Tutorial tutorial = new Tutorial("T8", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial);

        sca.addGrade("Midterm", 85.0);
        sca.addGrade("Final", 95.0);
        assertEquals(90.0, sca.getOverallScore(), 0.001);
    }
}


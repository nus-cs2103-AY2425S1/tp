package tahub.contacts.model.studentcourseassociation;

import org.junit.jupiter.api.Test;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Phone;
import seedu.address.model.GradingSystem;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.courseclass.tutorial.Tutorial;
import tahub.contacts.model.courseclass.recitation.Recitation;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class StudentCourseAssociationTest {
    @Test
    void testStudentCourseAssociation_Tutorial_AaronTanTuckChoy() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A1234567Z");
        Person student = new Person(matricNumber, new Name("Aaron Tan Tuck Choy"), new Phone("12345678"),
                                    new Email("aaron_tan@example.com"), new Address("Kent Ridge"), tags);
        Course course = new Course("CS1101S", "Programming Methodology");
        Tutorial tutorial = new Tutorial("T01", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, grades, tutorial);
        assertNotNull(sca);
        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(tutorial, sca.getCourseClass());
        assertSame(grades, sca.getGrades());
    }

    @Test
    void testStudentCourseAssociation_Tutorial_TerenceSimTjark() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A7654321M");
        Person student = new Person(matricNumber, new Name("Terence Sim Tjark"), new Phone("88776655"),
                                    new Email("terence_sim@example.com"), new Address("Kent Ridge"), tags);
        Course course = new Course("CS2030", "Programming Methodology 2");
        Tutorial tutorial = new Tutorial("T02", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, grades, tutorial);
        assertNotNull(sca);
        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(tutorial, sca.getCourseClass());
        assertSame(grades, sca.getGrades());
    }
    @Test
    void testStudentCourseAssociation_Tutorial_BenLeong() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A8090506Q");
        Person student = new Person(matricNumber, new Name("Ben Leong"), new Phone("99887766"),
                                    new Email("ben_leong@example.com"), new Address("Kent Ridge"), tags);
        Course course = new Course("CS3230", "Design and Analysis of Algorithms");
        Tutorial tutorial = new Tutorial("T03", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, grades, tutorial);
        assertNotNull(sca);
        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(tutorial, sca.getCourseClass());
        assertSame(grades, sca.getGrades());
    }
    @Test
    void testStudentCourseAssociation_Recitation_ColinTan() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A5394067G");
        Person student = new Person(matricNumber, new Name("Colin Tan"), new Phone("66778899"),
                                    new Email("colin_tan@example.com"), new Address("Kent Ridge"), tags);
        Course course = new Course("CS2020", "Data Structures and Algorithms Accelerated");
        Recitation recitation = new Recitation("R01", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, grades, recitation);
        assertNotNull(sca);
        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(recitation, sca.getCourseClass());
        assertSame(grades, sca.getGrades());
    }

    @Test
    void testStudentCourseAssociation_Recitation_HanryHam() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A0987765N");
        Person student = new Person(matricNumber, new Name("Henry Ham"), new Phone("99663322"),
                                    new Email("henry_ham@example.com"), new Address("Kent Ridge"), tags);
        Course course = new Course("CS2040", "Data Structures and Algorithms");
        Recitation recitation = new Recitation("R02", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, grades, recitation);
        assertNotNull(sca);
        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(recitation, sca.getCourseClass());
        assertSame(grades, sca.getGrades());
    }
    
    @Test
    void testStudentCourseAssociation_Recitation_StanleyHalim() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A7755331U");
        Person student = new Person(matricNumber, new Name("Stanley Halim"), new Phone("33558899"),
                                    new Email("stanley_halim@example.com"), new Address("Kent Ridge"), tags);
        Course course = new Course("CS1010", "Programming Methodology");
        Recitation recitation = new Recitation("R03", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, grades, recitation);
        assertNotNull(sca);
        assertSame(student, sca.getStudent());
        assertSame(course, sca.getCourse());
        assertSame(recitation, sca.getCourseClass());
        assertSame(grades, sca.getGrades());
    }

    @Test
    void testEquals_SameStudents_And_SameCourses_And_SameCourseClass() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A9999999C");
        Person student = new Person(matricNumber, new Name("James Tan"), new Phone("99999999"),
                                    new Email("james_tan@example.com"), new Address("999 Boulevard"), tags);
        Course course = new Course("CS3231", "Software Engineering");
        Tutorial tutorial = new Tutorial("T04", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course, grades, tutorial);
        Person otherStudent = new Person(matricNumber, new Name("James Tan"), new Phone("99999999"),
                                    new Email("james_tan@example.com"), new Address("999 Boulevard"), tags);
        Course otherCourse = new Course("CS3231", "Software Engineering");
        Tutorial otherTutorial = new Tutorial("T04", otherCourse);
        GradingSystem otherGrades = new GradingSystem();
        StudentCourseAssociation sca2 = new StudentCourseAssociation(otherStudent, otherCourse, otherGrades, otherTutorial);
        assertEquals(sca1, sca2);
    }

    @Test
    void testEquals_SameStudents_And_SameCourses_But_DifferentCourseClass() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A9999999C");
        Person student = new Person(matricNumber, new Name("James Tan"), new Phone("99999999"),
                                    new Email("james_tan@example.com"), new Address("999 Boulevard"), tags);
        Course course = new Course("CS3231", "Software Engineering");
        Tutorial tutorial = new Tutorial("T04", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course, grades, tutorial);
        Recitation differentRecitation = new Recitation("R04", course);
        StudentCourseAssociation sca3 = new StudentCourseAssociation(student, course, grades, differentRecitation);
        assertFalse(sca1.equals(sca3));
    }
    
    @Test
    void testEquals_DifferentStudents_And_SameCourses_And_SameCourseClass() {
        Set<Tag> tags = new HashSet<>();
        MatriculationNumber matricNumber = new MatriculationNumber("A9999999C");
        Person student = new Person(matricNumber, new Name("James Tan"), new Phone("99999999"),
                                    new Email("james_tan@example.com"), new Address("999 Boulevard"), tags);
        Course course = new Course("CS3231", "Software Engineering");
        Tutorial tutorial = new Tutorial("T04", course);
        GradingSystem grades = new GradingSystem();
        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course, grades, tutorial);
        MatriculationNumber differentMatricNumber = new MatriculationNumber("A8888888B");
        Person differentStudent = new Person(differentMatricNumber, new Name("Johnny Tan"), new Phone("88888888"),
                                    new Email("johnny_tan@example.com"), new Address("888 Boulevard"), tags);
        StudentCourseAssociation sca4 = new StudentCourseAssociation(differentStudent, course, grades, tutorial);
        assertFalse(sca1.equals(sca4));
    }
}

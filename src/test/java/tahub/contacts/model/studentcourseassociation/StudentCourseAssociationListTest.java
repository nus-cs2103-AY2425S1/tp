package tahub.contacts.model.studentcourseassociation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;
import tahub.contacts.model.tag.Tag;
import tahub.contacts.model.tutorial.Tutorial;

public class StudentCourseAssociationListTest {

    private StudentCourseAssociationList scaList;
    private Person student1;
    private Person student2;
    private Course course1;
    private Course course2;
    private Tutorial tutorial1;
    private Tutorial tutorial2;
    private StudentCourseAssociation sca1;
    private StudentCourseAssociation sca2;
    private StudentCourseAssociation sca3;

    @BeforeEach
    public void setUp() {
        scaList = new StudentCourseAssociationList();
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag1"));

        student1 = new Person(new MatriculationNumber("A1234567X"), new Name("Alice"),
                new Phone("12345678"), new Email("student1@example.com"), new Address("123 Street"), tags);
        student2 = new Person(new MatriculationNumber("A7654321X"), new Name("Amy"),
                new Phone("87654321"), new Email("student2@example.com"), new Address("456 Avenue"), tags);
        course1 = new Course("CS1010", "Introduction to CS");
        course2 = new Course("CS2020", "Data Structures");
        tutorial1 = new Tutorial("T01", course1);
        tutorial2 = new Tutorial("T02", course2);

        sca1 = new StudentCourseAssociation(student1, course1, tutorial1);
        sca2 = new StudentCourseAssociation(student1, course2, tutorial2);
        sca3 = new StudentCourseAssociation(student2, course1, tutorial1);

        scaList.add(sca1);
        scaList.add(sca2);
        scaList.add(sca3);
    }

    @Test
    public void filterScasByStudent() {
        StudentCourseAssociationList filteredScas = scaList.filterScasByStudent(student1);
        ObservableList<StudentCourseAssociation> expectedList = FXCollections.observableArrayList(sca1, sca2);
        assertEquals(expectedList, filteredScas.get());
    }

    @Test
    public void filterCoursesByStudent() {
        UniqueCourseList filteredCourses = scaList.filterCoursesByStudent(student1);
        UniqueCourseList expectedCourses = new UniqueCourseList();
        expectedCourses.add(course1);
        expectedCourses.add(course2);
        assertEquals(expectedCourses, filteredCourses);
    }

    @Test
    public void filterTutorialsByStudent() {
        List<Tutorial> filteredTutorials = scaList.filterTutorialsByStudent(student1);
        List<Tutorial> expectedTutorials = List.of(tutorial1, tutorial2);
        assertEquals(expectedTutorials, filteredTutorials);
    }
}

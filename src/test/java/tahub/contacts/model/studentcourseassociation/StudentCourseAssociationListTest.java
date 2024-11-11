package tahub.contacts.model.studentcourseassociation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;
import tahub.contacts.model.studentcourseassociation.exceptions.ScaNotFoundException;
import tahub.contacts.model.tag.Tag;
import tahub.contacts.model.tutorial.Tutorial;
import tahub.contacts.testutil.PersonBuilder;

public class StudentCourseAssociationListTest {

    private StudentCourseAssociationList scaList;
    private Person student1;
    private Person student2;
    private Person nonExistingStudent;
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
                              new Phone("12345678"), new Email("student1@example.com"), new Address("123 Street"),
                              tags);
        student2 = new Person(new MatriculationNumber("A7654321X"), new Name("Amy"),
                              new Phone("87654321"), new Email("student2@example.com"), new Address("456 Avenue"),
                              tags);
        nonExistingStudent = new Person(new MatriculationNumber("A9999999X"),
                                        new Name("NonExistent"), new Phone("99999999"), new Email("na@example.com"),
                                        new Address("999 Street"), new HashSet<>());
        course1 = new Course(new CourseCode("CS1010"), new CourseName("Introduction to CS"));
        course2 = new Course(new CourseCode("CS2020"), new CourseName("Data Structures"));
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

    @Nested
    @DisplayName("getFromStrings()")
    class GetFromStrings {
        @Test
        @DisplayName("finds correct SCA if SCA is in list")
        public void findsSca_scaInList() {
            StudentCourseAssociation foundSca = scaList.getFromStrings(
                    "A1234567X", "CS1010", "T01");
            assertEquals(foundSca, sca1);
        }

        @Test
        @DisplayName("throws runtime exception if SCA is not in list but inputs are valid")
        public void throwsRuntimeException_scaNotInListInputsValid() {
            assertThrows(ScaNotFoundException.class, () -> scaList.getFromStrings(
                    "A1234567X", "AB2000", "T02"));
        }
    }

    @Nested
    @DisplayName("findMatch()")
    class FindMatch {
        @Test
        @DisplayName("finds correct SCA if SCA with same matricNumber, courseCode, tutorialId is in list")
        public void findsSca_scaInList() {
            Course course = new Course(new CourseCode("CS1010"), new CourseName("Different Name"));
            StudentCourseAssociation toFind = new StudentCourseAssociation(
                    new PersonBuilder().withMatriculationNumber("A1234567X").build(),
                    course,
                    new Tutorial("T01", course)
            );
            StudentCourseAssociation foundSca = scaList.findMatch(toFind);
            assertEquals(foundSca, sca1);
        }

        @Test
        @DisplayName("throws ScaNotFoundException if SCA with same IDs is not in list")
        public void throwsScaNotFoundException_scaNotInListInputsValid() {
            Course course = new Course(new CourseCode("AA0000"), new CourseName("Different Name"));
            StudentCourseAssociation toFind = new StudentCourseAssociation(
                    new PersonBuilder().withMatriculationNumber("A4529999X").build(),
                    course,
                    new Tutorial("T99", course)
            );

            assertThrows(ScaNotFoundException.class, () -> scaList.findMatch(toFind));
        }
    }

    @Test
    public void get_byStudent_returnsCorrectScas() {
        ObservableList<StudentCourseAssociation> expectedScas = FXCollections.observableArrayList(sca1, sca2);
        assertEquals(expectedScas, scaList.get(student1));
    }

    @Test
    public void get_byCourse_returnsCorrectScas() {
        ObservableList<StudentCourseAssociation> expectedScas = FXCollections.observableArrayList(sca1, sca3);
        assertEquals(expectedScas, scaList.get(course1));
    }

    @Test
    public void get_byStudentAndCourse_returnsCorrectScas() {
        ObservableList<StudentCourseAssociation> expectedScas = FXCollections.observableArrayList(sca1);
        assertEquals(expectedScas, scaList.get(student1, course1));
    }

    @Test
    public void get_byCourseAndTutorial_returnsCorrectScas() {
        ObservableList<StudentCourseAssociation> expectedScas = FXCollections.observableArrayList(sca1, sca3);
        assertEquals(expectedScas, scaList.get(course1, tutorial1));
    }

    @Test
    public void getByMatric_returnsCorrectScas() {
        ObservableList<StudentCourseAssociation> expectedScas = FXCollections.observableArrayList(sca1, sca2);
        assertEquals(expectedScas, scaList.getByMatric("A1234567X"));
    }

    @Test
    public void filterScasByStudent_noMatchingScas_returnsEmptyList() {
        StudentCourseAssociationList filteredScas = scaList.filterScasByStudent(nonExistingStudent);
        ObservableList<StudentCourseAssociation> expectedList = FXCollections.observableArrayList();
        assertEquals(expectedList, filteredScas.get());
    }

    @Test
    public void filterCoursesByStudent_noMatchingCourses_returnsEmptyList() {
        UniqueCourseList filteredCourses = scaList.filterCoursesByStudent(nonExistingStudent);
        UniqueCourseList expectedCourses = new UniqueCourseList();
        assertEquals(expectedCourses, filteredCourses);
    }

    @Test
    public void filterTutorialsByStudent_noMatchingTutorials_returnsEmptyList() {
        List<Tutorial> filteredTutorials = scaList.filterTutorialsByStudent(nonExistingStudent);
        List<Tutorial> expectedTutorials = List.of();
        assertEquals(expectedTutorials, filteredTutorials);
    }

    @Test
    public void add_duplicateSca_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> scaList.add(sca1));
    }

    @Test
    public void set_targetNotInList_throwsRuntimeException() {
        StudentCourseAssociation newSca = new StudentCourseAssociation(student1, course1, tutorial2);
        assertThrows(RuntimeException.class, () -> scaList.set(newSca, sca2));
    }

    @Test
    public void set_duplicateSca_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> scaList.set(sca1, sca2));
    }

    @Test
    public void set_replacementList() {
        StudentCourseAssociationList replacementList = new StudentCourseAssociationList();
        replacementList.add(sca1);
        replacementList.add(sca2);
        scaList.set(replacementList);
        assertEquals(replacementList, scaList);
    }

    @Test
    public void set_validSca_editedSca() {
        StudentCourseAssociation editedSca = new StudentCourseAssociation(student1, course1, tutorial2);
        scaList.set(sca1, editedSca);
        assertFalse(scaList.get().contains(sca1));
        assertTrue(scaList.get().contains(editedSca));
    }

    @Test
    public void set_listWithDuplicates_throwsRuntimeException() {
        List<StudentCourseAssociation> duplicateList = List.of(sca1, sca1);
        assertThrows(RuntimeException.class, () -> scaList.set(duplicateList));
    }

    @Test
    public void set_validList_replacesList() {
        List<StudentCourseAssociation> newList = List.of(sca1, sca2);
        scaList.set(newList);
        assertEquals(newList, scaList.get());
    }

    @Test
    public void remove_existingSca_removesSca() {
        scaList.remove(sca1);
        assertFalse(scaList.get().contains(sca1));
    }

    @Test
    public void remove_nonExistingSca_throwsRuntimeException() {
        StudentCourseAssociation nonExistingSca = new StudentCourseAssociation(student2, course2, tutorial2);
        assertThrows(RuntimeException.class, () -> scaList.remove(nonExistingSca));
    }

    @Test
    public void remove_course_removesScasWithCourse() {
        scaList.remove(course1);
        assertFalse(scaList.get().stream().anyMatch(sca -> sca.getCourse().equals(course1)));
    }

    @Test
    public void asUnmodifiableObservableList_returnsUnmodifiableList() {
        ObservableList<StudentCourseAssociation> unmodifiableList = scaList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(sca1));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(scaList.equals(scaList));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(scaList.equals(new Object()));
    }

    @Test
    public void equals_differentList_returnsFalse() {
        StudentCourseAssociationList differentList = new StudentCourseAssociationList();
        assertFalse(scaList.equals(differentList));
    }

    @Test
    public void hashCode_sameList_returnsSameHashCode() {
        StudentCourseAssociationList sameList = new StudentCourseAssociationList();
        sameList.add(sca1);
        sameList.add(sca2);
        sameList.add(sca3);
        assertEquals(scaList.hashCode(), sameList.hashCode());
    }

    @Test
    public void toString_returnsCorrectString() {
        String expectedString = scaList.get().toString();
        assertEquals(expectedString, scaList.toString());
    }
}

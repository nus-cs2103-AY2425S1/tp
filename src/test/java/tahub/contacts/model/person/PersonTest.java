package tahub.contacts.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_MATRICULATION_NUMBER_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tahub.contacts.testutil.Assert.assertThrows;
import static tahub.contacts.testutil.TypicalPersons.ALICE;
import static tahub.contacts.testutil.TypicalPersons.BOB;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import tahub.contacts.logic.Logic;
import tahub.contacts.logic.LogicManager;
import tahub.contacts.model.Model;
import tahub.contacts.model.ModelManager;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.tag.Tag;
import tahub.contacts.model.tutorial.Tutorial;
import tahub.contacts.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same matriculation number, same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same matriculation number, different name, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same matriculation number, name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        Person editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // matriculation number differs in case, all other attributes same -> returns false
        editedBob = new PersonBuilder(BOB).withMatriculationNumber(VALID_MATRICULATION_NUMBER_AMY).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void getStudentCourseAssociations_validStudent_returnsCorrectScas() {
        Person student = new Person(new MatriculationNumber("A1234567X"), new Name("Alice"), new Phone("12345678"),
                new Email("student1@example.com"), new Address("123 Street"), new HashSet<Tag>());
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Introduction to CS"));
        Tutorial tutorial = new Tutorial("T01", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial);

        Model model = new ModelManager();
        model.addSca(sca);
        Logic logic = new LogicManager(model, null);

        StudentCourseAssociationList scaList = student.getStudentCourseAssociations(logic);
        assertEquals(1, scaList.asUnmodifiableObservableList().size());
        assertEquals(sca, scaList.asUnmodifiableObservableList().get(0));
    }

    @Test
    public void getCourses_validStudent_returnsCorrectCourses() {
        Person student = new Person(new MatriculationNumber("A1234567X"), new Name("Alice"), new Phone("12345678"),
                new Email("student1@example.com"), new Address("123 Street"), new HashSet<Tag>());
        Course course1 = new Course(new CourseCode("CS1010"), new CourseName("Introduction to CS"));
        Course course2 = new Course(new CourseCode("CS2020"), new CourseName("Data Structures"));
        Tutorial tutorial1 = new Tutorial("T01", course1);
        Tutorial tutorial2 = new Tutorial("T02", course2);
        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course1, tutorial1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(student, course2, tutorial2);

        Model model = new ModelManager();
        model.addSca(sca1);
        model.addSca(sca2);
        Logic logic = new LogicManager(model, null);

        UniqueCourseList courseList = student.getCourses(logic);
        assertEquals(2, courseList.asUnmodifiableObservableList().size());
        assertTrue(courseList.hasCourse(course1));
        assertTrue(courseList.hasCourse(course2));
    }

    @Test
    public void getTutorials_validStudent_returnsCorrectTutorials() {
        Person student = new Person(new MatriculationNumber("A1234567X"), new Name("Alice"), new Phone("12345678"),
                new Email("student1@example.com"), new Address("123 Street"), new HashSet<Tag>());
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Introduction to CS"));
        Tutorial tutorial1 = new Tutorial("T01", course);
        Tutorial tutorial2 = new Tutorial("T02", course);
        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course, tutorial1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(student, course, tutorial2);

        Model model = new ModelManager();
        model.addSca(sca1);
        model.addSca(sca2);
        Logic logic = new LogicManager(model, null);

        List<Tutorial> tutorials = student.getTutorials(logic);
        assertEquals(2, tutorials.size());
        assertTrue(tutorials.contains(tutorial1));
        assertTrue(tutorials.contains(tutorial2));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{matricNumber=" + ALICE.getMatricNumber()
                + ", name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}

package tahub.contacts.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static tahub.contacts.testutil.Assert.assertThrows;
import static tahub.contacts.testutil.TypicalPersons.ALICE;
import static tahub.contacts.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tahub.contacts.commons.core.GuiSettings;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.course.exceptions.CourseNotFoundException;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.NameContainsKeywordsPredicate;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.tutorial.Tutorial;
import tahub.contacts.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private Course course;
    private ModelManager modelManager = new ModelManager();
    private StudentCourseAssociation sca;

    @BeforeEach
    public void setUp() {
        sca = new StudentCourseAssociation(ALICE, new Course(new CourseCode("CS1010"),
                new CourseName("Introduction to CS")), new Tutorial("T01", new Course(new CourseCode("CS1010"),
                    new CourseName("Introduction to CS"))));
        course = new Course(new CourseCode("CS1010"), new CourseName("Introduction to CS"));
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new UniqueCourseList(), modelManager.getCourseList());
    }

    @Test
    public void addCourse_updatesFilteredPersonList() {
        modelManager.addPerson(ALICE); // Ensure there is at least one person in the address book
        modelManager.addCourse(course);
        assertFalse(modelManager.getFilteredPersonList().isEmpty());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void setCourseListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCourseListFilePath(null));
    }

    @Test
    public void setCourseListFilePath_validPath_setsCourseListFilePath() {
        Path path = Paths.get("course/list/file/path");
        modelManager.setCourseListFilePath(path);
        assertEquals(path, modelManager.getCourseListFilePath());
    }

    @Test
    public void getCourseListFilePath_returnsCorrectPath() {
        Path path = Paths.get("course/list/file/path");
        modelManager.setCourseListFilePath(path);
        assertEquals(path, modelManager.getCourseListFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasCourse_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCourse(null));
    }

    @Test
    public void hasCourse_courseNotInCourseList_returnsFalse() {
        assertFalse(modelManager.hasCourse(course));
    }

    @Test
    public void hasCourse_courseInCourseList_returnsTrue() {
        modelManager.addCourse(course);
        assertTrue(modelManager.hasCourse(course));
    }

    @Test
    public void deleteCourse_courseInCourseList_deletesCourse() {
        modelManager.addCourse(course);
        modelManager.deleteCourse(course);
        assertFalse(modelManager.hasCourse(course));
    }

    @Test
    public void addCourse_validCourse_addsCourse() {
        modelManager.addCourse(course);
        assertTrue(modelManager.hasCourse(course));
    }

    @Test
    public void getScaListFilePath_returnsCorrectPath() {
        Path path = Paths.get("sca/list/file/path");
        modelManager.setScaListFilePath(path);
        assertEquals(path, modelManager.getScaListFilePath());
    }

    @Test
    public void setScaListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setScaListFilePath(null));
    }

    @Test
    public void setScaListFilePath_validPath_setsScaListFilePath() {
        Path path = Paths.get("sca/list/file/path");
        modelManager.setScaListFilePath(path);
        assertEquals(path, modelManager.getScaListFilePath());
    }

    @Test
    public void hasSca_nullSca_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSca(null));
    }

    @Test
    public void hasSca_scaNotInScaList_returnsFalse() {
        assertFalse(modelManager.hasSca(sca));
    }

    @Test
    public void hasSca_scaInScaList_returnsTrue() {
        modelManager.addSca(sca);
        assertTrue(modelManager.hasSca(sca));
    }

    @Test
    public void deleteSca_scaInScaList_deletesSca() {
        modelManager.addSca(sca);
        modelManager.deleteSca(sca);
        assertFalse(modelManager.hasSca(sca));
    }

    @Test
    public void addSca_validSca_addsSca() {
        modelManager.addSca(sca);
        assertTrue(modelManager.hasSca(sca));
    }

    @Test
    public void getScaList_returnsCorrectScaList() {
        modelManager.addSca(sca);
        StudentCourseAssociationList scaList = modelManager.getScaList();
        assertTrue(scaList.contains(sca));
    }

    @Test
    public void setCourse_nullTargetCourse_throwsNullPointerException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        Assertions.assertThrows(NullPointerException.class, () -> modelManager.setCourse(null, course));
    }

    @Test
    public void setCourse_nullEditedCourse_throwsNullPointerException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        modelManager.addCourse(course);
        Assertions.assertThrows(NullPointerException.class, () -> modelManager.setCourse(course, null));
    }

    @Test
    public void setCourse_targetCourseNotInCourseList_throwsCourseNotFoundException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        Course editedCourse = new Course(new CourseCode("CS1020"), new CourseName("Data Structures"));
        Assertions.assertThrows(CourseNotFoundException.class, () -> modelManager.setCourse(course, editedCourse));
    }

    @Test
    public void setCourse_editedCourseIsSameCourse_success() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        modelManager.addCourse(course);
        modelManager.setCourse(course, course);

        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addCourse(course);

        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void setCourse_editedCourseHasSameIdentity_success() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        modelManager.addCourse(course);
        Course editedCourse = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        modelManager.setCourse(course, editedCourse);

        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addCourse(course);

        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void setCourse_editedCourseHasDifferentIdentity_success() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        modelManager.addCourse(course);
        Course editedCourse = new Course(new CourseCode("CS1020"), new CourseName("Data Structures"));
        modelManager.setCourse(course, editedCourse);

        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addCourse(course);

        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void setCourseList_validCourseList_setsCourseList() {
        UniqueCourseList courseList = new UniqueCourseList();
        courseList.addCourse(course);
        modelManager.setCourseList(courseList);
        assertTrue(modelManager.hasCourse(course));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        UniqueCourseList courseList = new UniqueCourseList();
        StudentCourseAssociationList scaList = new StudentCourseAssociationList();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, courseList, scaList);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, courseList, scaList);
        assertEquals(modelManager, modelManagerCopy);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different addressBook -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAddressBook, userPrefs, courseList, scaList));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(addressBook, userPrefs, courseList, scaList));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(addressBook, differentUserPrefs, courseList, scaList));
    }

    @Test
    public void getStudentScas_validStudent_returnsCorrectScas() {
        Person student = new Person(new MatriculationNumber("A1234567X"),
                new Name("Alice"), new Phone("12345678"), new Email("student1@example.com"),
                new Address("123 Street"), new HashSet<>());
        Tutorial tutorial = new Tutorial("T01", course);
        StudentCourseAssociation sca = new StudentCourseAssociation(student, course, tutorial);
        modelManager.addSca(sca);

        StudentCourseAssociationList scaList = modelManager.getStudentScas(student);
        assertTrue(scaList.contains(sca));
    }

    @Test
    public void getStudentCourses_validStudent_returnsCorrectCourses() {
        Person student = new Person(new MatriculationNumber("A1234567X"),
                new Name("Alice"), new Phone("12345678"), new Email("student1@example.com"),
                new Address("123 Street"), new HashSet<>());
        Course course1 = new Course(new CourseCode("CS1010"), new CourseName("Introduction to CS"));
        Course course2 = new Course(new CourseCode("CS2020"), new CourseName("Data Structures"));
        Tutorial tutorial1 = new Tutorial("T01", course1);
        Tutorial tutorial2 = new Tutorial("T02", course2);
        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course1, tutorial1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(student, course2, tutorial2);
        modelManager.addSca(sca1);
        modelManager.addSca(sca2);

        UniqueCourseList courseList = modelManager.getStudentCourses(student);
        assertTrue(courseList.hasCourse(course1));
        assertTrue(courseList.hasCourse(course2));
    }

    @Test
    public void getStudentTutorials_validStudent_returnsCorrectTutorials() {
        Person student = new Person(new MatriculationNumber("A1234567X"),
                new Name("Alice"), new Phone("12345678"), new Email("student1@example.com"),
                new Address("123 Street"), new HashSet<>());
        Tutorial tutorial1 = new Tutorial("T01", course);
        Tutorial tutorial2 = new Tutorial("T02", course);
        StudentCourseAssociation sca1 = new StudentCourseAssociation(student, course, tutorial1);
        StudentCourseAssociation sca2 = new StudentCourseAssociation(student, course, tutorial2);
        modelManager.addSca(sca1);
        modelManager.addSca(sca2);

        List<Tutorial> tutorials = modelManager.getStudentTutorials(student);
        assertTrue(tutorials.contains(tutorial1));
        assertTrue(tutorials.contains(tutorial2));
    }
}

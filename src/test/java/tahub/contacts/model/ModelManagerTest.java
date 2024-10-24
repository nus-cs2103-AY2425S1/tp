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

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.core.GuiSettings;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.NameContainsKeywordsPredicate;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new UniqueCourseList(), modelManager.getCourseList());
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
        Course course = new Course("CS1010", "Introduction to CS");
        assertFalse(modelManager.hasCourse(course));
    }

    @Test
    public void hasCourse_courseInCourseList_returnsTrue() {
        Course course = new Course("CS1010", "Introduction to CS");
        modelManager.addCourse(course);
        assertTrue(modelManager.hasCourse(course));
    }

    @Test
    public void deleteCourse_courseInCourseList_deletesCourse() {
        Course course = new Course("CS1010", "Introduction to CS");
        modelManager.addCourse(course);
        modelManager.deleteCourse(course);
        assertFalse(modelManager.hasCourse(course));
    }

    @Test
    public void addCourse_validCourse_addsCourse() {
        Course course = new Course("CS1010", "Introduction to CS");
        modelManager.addCourse(course);
        assertTrue(modelManager.hasCourse(course));
    }

    @Test
    public void setCourseList_validCourseList_setsCourseList() {
        UniqueCourseList courseList = new UniqueCourseList();
        Course course = new Course("CS1010", "Introduction to CS");
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
}
